package com.config;

import com.agents.parallel.EveningPlannerAgent;
import dev.langchain4j.agentic.AgenticServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ParallelAgentConfig {

    @Bean
    public EveningPlannerAgent eveningPlannerAgent() {
        return AgenticServices.createAgenticSystem(EveningPlannerAgent.class);
    }

}