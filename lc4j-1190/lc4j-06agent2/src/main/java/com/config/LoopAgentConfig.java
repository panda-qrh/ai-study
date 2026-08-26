package com.config;

import com.agents.loop.StyleScorer;
import com.agents.loop.StyledWriter;
import com.agents.sequence.CreativeWriter;
import com.agents.sequence.StyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentMonitor;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoopAgentConfig {



    @Bean
    public StyleScorer styleScorer(ChatModel chatModel) {
        return AgenticServices.agentBuilder(StyleScorer.class)
                .chatModel(chatModel)
                .outputKey("score")
                .build();
    }
    @Bean
    public StyledWriter styledWriter(CreativeWriter creativeWriter, UntypedAgent styleReviewLoop) {
        return AgenticServices.sequenceBuilder(StyledWriter.class)
                .subAgents(creativeWriter, styleReviewLoop)
                .outputKey("story")
                .build();
    }

    @Bean
    public UntypedAgent styleReviewLoop(StyleScorer styleScorer, StyleEditor styleEditor) {
        return AgenticServices.loopBuilder()
                .subAgents(styleScorer, styleEditor)
                .maxIterations(5)
                .exitCondition(agenticScope -> agenticScope.readState("score", 0.0) >= 0.8)
                .build();
    }

    @Bean
    public UntypedAgent untypedStyledWriter(CreativeWriter creativeWriter, UntypedAgent styleReviewLoop) {
        return AgenticServices.sequenceBuilder()
                .subAgents(creativeWriter, styleReviewLoop)
                .listener(new AgentListener() { //监控
                    @Override
                    public void afterAgentInvocation(AgentResponse agentResponse) {
                        System.out.println("agentName= "+agentResponse.agentName());
                        if (agentResponse.agentName().equals("styleScorer")) {
                            System.out.println("当前分数： " + agentResponse.output());
                        }
                    }
                })
                .outputKey("story")
                .build();
    }


}