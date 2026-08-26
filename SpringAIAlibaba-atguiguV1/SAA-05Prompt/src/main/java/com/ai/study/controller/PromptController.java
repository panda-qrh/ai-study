package com.ai.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class PromptController {
    @Resource
    public StreamingChatModel streamingChatModel;

    @Resource
    private ChatClient chatClient;

    @GetMapping("/prompt")
    public Flux<String> chat(String msg) {
        return chatClient.prompt()
                .system("你是一个专业的java架构师，负责回答有关于Java及Java项目架构的问题，其它问题一律拒绝回答")
                .user(msg)
                .stream()
                .content();
    }

    @GetMapping("/prompt/2")
    public Flux<String> chat2(String msg) {

        return streamingChatModel.stream(
                        Prompt.builder()
                                .messages(
                                        SystemMessage.builder().text("你是一个讲故事的高手，专门讲黑色幽默笑话，每个故事控制在300字以内").build(),
                                        UserMessage.builder().text(msg).build()
                                )
                                .build())
                .map(response -> (response.getResult() == null || response.getResult().getOutput() == null
                        || response.getResult().getOutput().getText() == null) ? ""
                        : response.getResult().getOutput().getText());
    }

}
