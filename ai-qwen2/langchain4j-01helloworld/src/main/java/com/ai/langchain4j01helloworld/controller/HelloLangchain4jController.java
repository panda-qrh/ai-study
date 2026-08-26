package com.ai.langchain4j01helloworld.controller;

import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloLangchain4jController {

    @Resource
    private ChatModel chatModelQwen;

    @GetMapping("/langchain4j/hello")
    public String hello(@RequestParam(value = "question",defaultValue = "你是谁？") String question){
       return chatModelQwen.chat(question);
    }
}
