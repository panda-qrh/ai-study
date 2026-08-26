package com.controller;

import com.agents.condition.ExpertRouterAgent;
import com.agents.parallelmapper.BatchHoroscopeAgent;
import com.agents.parallelmapper.Person;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ConditionalAgentController {
    @Resource
    private ExpertRouterAgent expertRouterAgent;


    @GetMapping("/experts")
    public String experts(@RequestParam(defaultValue = "我摔断腿了，怎么办？")String question) {
        return expertRouterAgent.ask(question);
    }



}