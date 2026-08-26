package com.ai.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamingChatController {

    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping("/chat")
    public String chat() {
        return chatModel.call("你是谁");
    }

    @GetMapping("/chat/stream")
    public Flux<String> chatStream() {
        return chatModel.stream("introduce the Thread Pool of Java");
    }

}
