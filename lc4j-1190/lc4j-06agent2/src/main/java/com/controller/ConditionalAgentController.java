package com.controller;

import com.agents.condition.ExpertRouterAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConditionalAgentController {
    @Resource
    private ExpertRouterAgent expertRouterAgent;


    @GetMapping("/experts")
    public String experts(@RequestParam(defaultValue = "我摔断腿了，怎么办？")String question) {
        return expertRouterAgent.ask(question);
    }



}