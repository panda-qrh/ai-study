package com.controller;

import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {
    @Resource
    private ChatModel chatModel;


    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "你好！") String  message) {
        return chatModel.chat(message);
    }

    @GetMapping("/hello2")
    public String hello2(@RequestParam(defaultValue = "你好！") String  message) {
        UserMessage userMessage = UserMessage.builder()
                .addContent(TextContent.from(message))
                .name("张大三")
                .attributes(Map.of("age", 33, "gender", "female", "weight", "178kg"))
                .build();
        ChatResponse response = chatModel.chat(userMessage);
        System.out.println(response);
        return response.aiMessage().toString();
    }
}