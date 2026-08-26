package com.config;

import com.agents.condition.*;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConditionalAgentConfig {


    @Bean
    public MedicalExpert medicalExpert(ChatModel chatModel) {
        return AgenticServices.agentBuilder(MedicalExpert.class)
                .chatModel(chatModel)
                .outputKey("response")
                .build();
    }

    @Bean
    public LegalExpert legalExpert(ChatModel chatModel) {
        return AgenticServices.agentBuilder(LegalExpert.class)
                .chatModel(chatModel)
                .outputKey("response")
                .build();
    }

    @Bean
    public TechnicalExpert technicalExpert(ChatModel chatModel) {
        return AgenticServices.agentBuilder(TechnicalExpert.class)
                .chatModel(chatModel)
                .outputKey("response")
                .build();
    }

    @Bean
    public CategoryRouter routerAgent(ChatModel chatModel) {
        return AgenticServices.agentBuilder(CategoryRouter.class)
                .chatModel(chatModel)
                .outputKey("category")
                .build();
    }

    @Bean
    public UntypedAgent expertsAgent(MedicalExpert medicalExpert, LegalExpert legalExpert, TechnicalExpert technicalExpert) {
        return AgenticServices.conditionalBuilder()
                .subAgents(agenticScope -> agenticScope.readState("category", RequestCategory.UNKNOWN) == RequestCategory.MEDICAL, medicalExpert)
                .subAgents(agenticScope -> agenticScope.readState("category", RequestCategory.UNKNOWN) == RequestCategory.LEGAL, legalExpert)
                .subAgents(agenticScope -> agenticScope.readState("category", RequestCategory.UNKNOWN) == RequestCategory.TECHNICAL, technicalExpert)
                .build();
    }

    @Bean
    public ExpertRouterAgent expertRouterAgent(CategoryRouter routerAgent, UntypedAgent expertsAgent) {
        return AgenticServices.sequenceBuilder(ExpertRouterAgent.class)
                .subAgents(routerAgent, expertsAgent)
                .outputKey("response")
                .build();
    }

}