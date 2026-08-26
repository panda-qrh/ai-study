package com.config;

import com.agents.goal_oriented_agentic_pattern.*;
import com.tools.WebSearchTool;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoalOrientedConfig {
    @Bean
    public UntypedAgent horoscopeAgent(ChatModel chatModel) {
        HoroscopeGenerator horoscopeGenerator=  AgenticServices.agentBuilder(HoroscopeGenerator.class)
                .chatModel(chatModel).outputKey("horoscope").build();
        PersonExtractor personExtractor = AgenticServices.agentBuilder(PersonExtractor.class)
                .chatModel(chatModel).outputKey("person").build();
        SignExtractor signExtractor  = AgenticServices.agentBuilder(SignExtractor.class)
                .chatModel(chatModel).outputKey("sign").build();
        Writer writer  = AgenticServices.agentBuilder(Writer.class)
                .chatModel(chatModel).outputKey("writeup").build();
        StoryFinder storyFinder = AgenticServices.agentBuilder(StoryFinder.class)
                .chatModel(chatModel).tools(new WebSearchTool()).outputKey("story").build();

        return AgenticServices.plannerBuilder()
                .subAgents(horoscopeGenerator, personExtractor, signExtractor, writer, storyFinder)
                .outputKey("writeup")
                .planner(GoalOrientedPlanner::new)
                .build();
    }
}
