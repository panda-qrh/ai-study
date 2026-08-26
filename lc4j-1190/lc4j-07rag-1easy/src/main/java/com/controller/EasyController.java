package com.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import com.aiservice.Assistant;

@RestController
public class EasyController {
    @Resource
    private Assistant assistant;

    @GetMapping("/easy-ask")
    public Flux<String> ask(@RequestParam(defaultValue = "鱼皮的求职指南里讲了什么") String ask) {
        return assistant.chat("user123456789",ask);
    }
}