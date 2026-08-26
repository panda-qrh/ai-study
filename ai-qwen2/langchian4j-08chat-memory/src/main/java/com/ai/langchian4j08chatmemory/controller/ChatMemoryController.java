package com.ai.langchian4j08chatmemory.controller;

import com.ai.langchian4j08chatmemory.service.ChatMemoryAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 原生框架，使用高阶api
 */
@RestController
public class ChatMemoryController {
    @Resource
    private ChatMemoryAssistant chatMessageWindowChatMemory;


    @GetMapping("/lc4j/chatmemory")
    public Flux<String> chatFlux( @RequestParam(defaultValue = "1")String memoryId,@RequestParam(value = "prompt", defaultValue = "who are you?") String prompt) {
        Flux<String> res = chatMessageWindowChatMemory.chatFlux(memoryId,prompt);
        return res;

    }
}
