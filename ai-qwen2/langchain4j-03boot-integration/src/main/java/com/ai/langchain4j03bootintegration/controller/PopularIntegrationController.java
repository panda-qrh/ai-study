package com.ai.langchain4j03bootintegration.controller;

import com.ai.langchain4j03bootintegration.service.AiAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * boot整合，使用低阶api
 */
@RestController
public class PopularIntegrationController {
    @Resource
    private AiAssistant  apiAssistant;

    @GetMapping("/lc4j/boot/declarative")
    public String chat(@RequestParam(value = "prompt",defaultValue = "who are you?")String prompt){
        String chat = apiAssistant.chat(prompt);
        return chat;
    }
}
