package com.ai.langchain4j04lowhighapi.controller;

import com.ai.langchain4j04lowhighapi.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *原生框架，使用高阶api
 */
@RestController
public class HighAPiController {
    @Resource
    private ChatAssistant chatAssistant;

    @GetMapping("/lc4j/highapi")
    public String chat(@RequestParam(value = "prompt",defaultValue = "who are you?")String prompt){
        String chat = chatAssistant.chat(prompt);
        return chat;
    }
}
