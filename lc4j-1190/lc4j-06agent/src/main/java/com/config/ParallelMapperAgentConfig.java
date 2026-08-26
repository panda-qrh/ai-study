package com.config;

import com.agents.parallelmapper.BatchHoroscopeAgent;
import com.agents.parallelmapper.PersonAstrologyAgent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class ParallelMapperAgentConfig {


    @Bean
   public PersonAstrologyAgent personAstrologyAgent(ChatModel chatModel) {
        return AgenticServices.agentBuilder(PersonAstrologyAgent.class)
                .chatModel(chatModel)
                .outputKey("horoscope")
                .build();
    }

    @Bean
    public BatchHoroscopeAgent agent(PersonAstrologyAgent personAstrologyAgent){
        return AgenticServices.parallelMapperBuilder(BatchHoroscopeAgent.class)
                .subAgents(personAstrologyAgent)
                .itemsProvider("persons")
                .executor(Executors.newFixedThreadPool(3))
                .build();

    }
}