package com.config;

import com.agents.custom_agentic_ai.AudienceEditor;
import com.agents.custom_agentic_ai.CreativeWriter;
import com.agents.custom_agentic_ai.StyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.workflow.impl.SequentialPlanner;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlannerConfig {
    @Bean
    public UntypedAgent novelCreator(ChatModel chatModel) {
        CreativeWriter creativeWriter = AgenticServices.agentBuilder(CreativeWriter.class).chatModel(chatModel).outputKey("story").build();
        AudienceEditor audienceEditor = AgenticServices.agentBuilder(AudienceEditor.class).chatModel(chatModel).outputKey("story").build();
        StyleEditor styleEditor = AgenticServices.agentBuilder(StyleEditor.class).chatModel(chatModel).outputKey("story").build();

        return AgenticServices.plannerBuilder()
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .planner(SequentialPlanner::new)
                .outputKey("story")
                .build();
        /*
        写法等价于：
             return AgenticServices.sequenceBuilder()
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .outputKey("story")
                .build();
         */
    }
}