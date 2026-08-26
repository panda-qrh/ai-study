package com.config;

import com.agents.p2p_agentic_pattern.*;
import com.tools.ArxivCrawler;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P2PConfig {

    @Bean
    public UntypedAgent researchAgent(ChatModel chatModel) {
        LiteratureAgent literatureAgent = AgenticServices.agentBuilder(LiteratureAgent.class)
                .chatModel(chatModel).tools(new ArxivCrawler()).outputKey("researchFindings").build();
        HypothesisAgent hypothesisAgent = AgenticServices.agentBuilder(HypothesisAgent.class)
                .chatModel(chatModel).outputKey("hypothesis").build();
        ValidationAgent validationAgent = AgenticServices.agentBuilder(ValidationAgent.class)
                .chatModel(chatModel).outputKey("hypothesis").build();
        CriticAgent criticAgent = AgenticServices.agentBuilder(CriticAgent.class)
                .chatModel(chatModel).outputKey("critique").build();
        ScorerAgent scorerAgent = AgenticServices.agentBuilder(ScorerAgent.class)
                .chatModel(chatModel).outputKey("score").build();

        return AgenticServices.plannerBuilder()
                .subAgents(literatureAgent, hypothesisAgent, criticAgent, validationAgent, scorerAgent)
                .outputKey("hypothesis")
                .planner(() -> new P2PPlanner(10, (AgenticScope agenticScope, Integer invocations) -> {
                    if (!agenticScope.hasState("score")) {
                        return false;
                    }
                    double score = agenticScope.readState("score", 0.0);
                    System.out.println("Current hypothesis score: " + score);
                    return score >= 0.85;
                }))
                .build();
    }
}
