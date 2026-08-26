package com.agents.condition;

import com.config.AiConfig;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import org.springframework.stereotype.Component;

//@Component
public interface MedicalExpert {

    @UserMessage("""
            你是医疗专家。
            从医疗角度分析以下用户请求，并提供最佳答案。
            用户请求为{{request}}。
            """)
    @Agent(value = "一个医疗专家",outputKey = "response")
    String medical(@V("request") String request);

    @ChatModelSupplier
    static ChatModel chatModel() {
        return new AiConfig().chatModel();
    }
}