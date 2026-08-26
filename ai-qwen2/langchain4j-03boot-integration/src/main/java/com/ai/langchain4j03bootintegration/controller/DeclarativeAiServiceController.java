package com.ai.langchain4j03bootintegration.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * boot整合，使用高阶api
 */
@RestController
public class DeclarativeAiServiceController {
    @Resource
    private ChatModel chatModel;

    @GetMapping("/lc4j/boot/chat")
    public String chat(@RequestParam(value = "prompt",defaultValue = "who are you?")String prompt){
        String chat = chatModel.chat(prompt);
        return chat;
    }
}
