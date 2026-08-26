package com.controller;

import com.agents.parallel.EveningPlan;
import com.agents.parallel.EveningPlannerAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ParallelAgentController {
    @Resource
    private EveningPlannerAgent eveningPlannerAgent;

    @GetMapping("/plan")
    public List<EveningPlan> plan(@RequestParam(defaultValue = "浪漫的") String mood) {
        return eveningPlannerAgent.plan(mood);
    }

}