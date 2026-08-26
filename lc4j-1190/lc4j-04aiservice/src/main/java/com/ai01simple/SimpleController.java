package com.ai01simple;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    @Resource
    private Assistant assistant;
    @Resource
    private ChatModel chatModel;

    @GetMapping("/simple")
    public String simple(String message) {
        return assistant.chat(message);
    }

    @GetMapping("/friend")
    public String friend(String message) {
        return assistant.chatFriend(message);
    }


    @GetMapping("/param")
    public String param(@RequestParam(defaultValue="讲一个冷笑话，200左右") String message) {
        ChatRequestParameters param = ChatRequestParameters.builder()
                .temperature(0.85)
                .build();
        return assistant.chatWithParam(message,param);
    }


}