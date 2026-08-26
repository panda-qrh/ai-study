package com.agents.parallel;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.Output;
import dev.langchain4j.agentic.declarative.ParallelAgent;
import dev.langchain4j.agentic.declarative.ParallelExecutor;
import dev.langchain4j.internal.Json;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface EveningPlannerAgent {

    @ParallelAgent(outputKey = "plans", subAgents = {FoodExpert.class, MovieExpert.class})
    List<EveningPlan> plan(@UserMessage @V("mood") String mood);

    @ParallelExecutor
    static Executor executor() {
        return Executors.newFixedThreadPool(2);
    }

    @Output
    static List<EveningPlan> createPlan(@V("movies") List<String> movies, @V("meals") List<String> meals) {
        List<EveningPlan> plans = new ArrayList<>();
        for (int i = 0; i < movies.size() && i < meals.size(); i++) {
            plans.add(new EveningPlan(meals.get(i), movies.get(i)));
        }
        return plans;
    }

}
