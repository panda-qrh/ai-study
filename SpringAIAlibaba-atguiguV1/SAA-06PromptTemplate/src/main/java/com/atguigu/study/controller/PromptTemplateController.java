package com.atguigu.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.PathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class PromptTemplateController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/prompt")
    public Flux<String> chat(@RequestParam(required = true) String topic,
                             @RequestParam(required = false, defaultValue = "HTML") String outputFormat,
                             @RequestParam(required = false, defaultValue = "1000") Integer wordLength) {

        Prompt prompt  = PromptTemplate.builder()
                .resource(new PathResource("E:\\A1_Projects\\Java_Projects\\ai\\SpringAIAlibaba-atguiguV1\\SAA-06PromptTemplate\\src\\main\\resources\\templates_contents\\template.txt"))
                .variables(Map.of("topic", topic, "output_format", outputFormat, "word_length", wordLength))
                .build()
                .create();

        return chatClient.prompt(prompt)
                .stream()
                .content();

    }


}
