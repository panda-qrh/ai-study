package com.ai.langchain4j04lowhighapi.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用原生框架不使用boot，学习低阶api
 */
@RestController
public class LowApiController {
    @Resource
    private ChatModel chatModelQwen;

    @GetMapping("/lc4j/lowapi")
    public String chat(@RequestParam(value = "prompt",defaultValue = "who are you?")String prompt){
        String chat = chatModelQwen.chat(prompt);
        return chat;
    }
}
