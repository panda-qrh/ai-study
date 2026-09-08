package com.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamController {
    @Resource
    private ChatClient chatClient;

    @GetMapping("/stream")
    public Flux<ChatResponse> streamChat(@RequestParam(defaultValue = "java中的stream怎么使用？") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .chatResponse();

    }
}