package com.controller;

import com.aiservice.Assistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AdvancedController {
    @Resource
    private Assistant assistant;

    @GetMapping("/ask-advanced")
    public Flux<String> askAdvanced(@RequestParam(defaultValue = "鱼皮的求职指南都写了什么？") String message) {
       return assistant.chat("user123", message);
    }
}