package com.ai.study.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MultiModelController {

    @Resource
    private ChatModel chatModelQwenPlus;

    @Resource
    private ChatModel chatModelQwenLong;

    @GetMapping("multi/plus")
    public String plus() {
        return chatModelQwenPlus.call("你用的模型名称叫什么？");
    }

    @GetMapping("multi/long")
    public Flux<String> qwenLong() {
        return chatModelQwenLong.stream("你用的模型名称叫什么？");
    }
}
