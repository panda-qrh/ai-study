package com.controller;

import jakarta.annotation.Resource;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Resource
    private ChatClient chatClient;
    @Resource
    private ChatClient customChatClient;


    @GetMapping("/hello")
    public  ChatResponse hello(@RequestParam(defaultValue = "你好，stepfun.com") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
    }

    @GetMapping("/java")
    public String javaer(@RequestParam(defaultValue = "你好，java 的stream流怎么使用") String message) {
        return customChatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}