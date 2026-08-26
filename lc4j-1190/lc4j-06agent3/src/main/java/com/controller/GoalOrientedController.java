package com.controller;

import dev.langchain4j.agentic.UntypedAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GoalOrientedController {
    @Resource
    private UntypedAgent horoscopeAgent;

    @GetMapping("/horoscope")
    public String createNovel() {
        Map<String, Object> input = Map.of("prompt", "My name is Zhangsan and my zodiac sign is pisces");
        return (String) horoscopeAgent.invoke(input);
    }


}