package com.agents.condition;

import com.config.AiConfig;
import dev.langchain4j.agentic.declarative.ActivationCondition;
import dev.langchain4j.agentic.declarative.BeforeCall;
import dev.langchain4j.agentic.declarative.ConditionalAgent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.V;

public interface ExpertRouterAgent {

    @ConditionalAgent(outputKey = "response", subAgents = {MedicalExpert.class, TechnicalExpert.class, LegalExpert.class})
    String ask(@V("request") String request);


    @BeforeCall
    static void beforeCall(AgenticScope scope) {
        if (scope.hasState("category")) {
            return;
        }
        String userRequest = (String) scope.readState("request");
//        if (userRequest == null) return;

        String categoryStr = classifyRequest(userRequest);
        RequestCategory category;
        try {
            category = RequestCategory.valueOf(categoryStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            category = RequestCategory.UNKNOWN;
        }
        scope.writeState("category", category);
    }

    private static String classifyRequest(String request) {
        try {
            ChatModel chatModel = new AiConfig().chatModel();
            String prompt = "你是一个分类助手。分析以下用户请求，"
                    + "将其分类为 legal, medical 或 technical。"
                    + "如果请求都不属于这三类，则归类为 unknown。"
                    + "只返回一个词，不要任何其他内容。\n\n"
                    + "用户请求：" + request;
            return chatModel.chat(prompt);
        } catch (Exception e) {
            return "unknown";
        }
    }

    @ActivationCondition(value = MedicalExpert.class)
    static boolean activateMedicalExpert(@V("category") RequestCategory category) {
        return category == RequestCategory.MEDICAL;
    }

    @ActivationCondition(value = TechnicalExpert.class)
    static boolean activateTechnicalExpert(@V("category") RequestCategory category) {
        return category == RequestCategory.TECHNICAL;
    }

    @ActivationCondition(value = LegalExpert.class)
    static boolean activateLegalExpert(@V("category") RequestCategory category) {
        return category == RequestCategory.LEGAL;
    }

    @ChatModelSupplier
    static ChatModel chatModel() {
        return new AiConfig().chatModel();
    }
}
