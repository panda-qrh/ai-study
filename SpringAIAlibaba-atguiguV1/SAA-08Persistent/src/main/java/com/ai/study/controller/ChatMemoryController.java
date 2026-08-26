package com.ai.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.PathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
public class ChatMemoryController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/memory")
    public Flux<String> chat(String msg,String userId) {


        return chatClient.prompt( )
                .advisors(advisorSpec -> advisorSpec.param("conversationId", userId))
                .user(msg)
                .stream()
                .content();

    }


}
