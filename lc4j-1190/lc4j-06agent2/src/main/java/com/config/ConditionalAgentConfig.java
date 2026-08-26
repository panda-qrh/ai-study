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
    public ExpertRouterAgent  expertRouterAgent() {
        return AgenticServices.createAgenticSystem(ExpertRouterAgent.class);
    }

}