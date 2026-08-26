package com.ai.langchain4j09prompt.controller;

import com.ai.langchain4j09prompt.pojo.QuestionPrompt;
import com.ai.langchain4j09prompt.service.ChatAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class ChatMemoryController {
    @Resource
    private ChatAssistant chatAssistant;


    @GetMapping("/lc4j/chat/prompt")
    public Flux<String> chatFlux( @RequestParam String question,@RequestParam Integer length) {
        Flux<String> res = chatAssistant.chatFlux(question,length);
        return res;

    }

    @GetMapping("/lc4j/chat/prompt2")
    public Flux<String> chatFlux2(QuestionPrompt questionPrompt){
        return chatAssistant.chatFlux(questionPrompt);
    }
}
