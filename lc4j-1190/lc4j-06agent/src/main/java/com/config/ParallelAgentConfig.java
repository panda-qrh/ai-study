package com.config;

import com.agents.parallel.EveningPlan;
import com.agents.parallel.EveningPlannerAgent;
import com.agents.parallel.FoodExpert;
import com.agents.parallel.MovieExpert;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class ParallelAgentConfig {

    @Bean
    public FoodExpert foodExpert(ChatModel chatModel) {
        return AgenticServices.agentBuilder(FoodExpert.class)
                .chatModel(chatModel)
                .outputKey("meals")
                .build();
    }

    @Bean
    public MovieExpert movieExpert(ChatModel chatModel) {
        return AgenticServices.agentBuilder(MovieExpert.class)
                .chatModel(chatModel)
                .outputKey("movies")
                .build();
    }


    @Bean
    public EveningPlannerAgent eveningPlannerAgent(FoodExpert foodExpert, MovieExpert movieExpert) {
        return AgenticServices.parallelBuilder(EveningPlannerAgent.class)
                .subAgents(foodExpert, movieExpert)
                .executor(Executors.newFixedThreadPool(2))
                .outputKey("plan")
                .output(agenticScope -> {
                    List<String> meals = agenticScope.readState("meals", List.of());
                    List<String> movies = agenticScope.readState("movies", List.of());

                    List<EveningPlan> movieAndMeals = new ArrayList<>();
                    for (int i = 0; i < movies.size(); i++) {
                        if (i >= meals.size()) {
                            break;
                        }
                        movieAndMeals.add(new EveningPlan(meals.get(i), movies.get(i)));
                    }
                    return movieAndMeals;
                })
                .build();
    }

}