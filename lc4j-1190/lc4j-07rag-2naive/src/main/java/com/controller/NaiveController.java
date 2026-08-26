package com.controller;

import com.aiservice.Assistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class NaiveController {
    @Resource
    private Assistant assistant;

    @GetMapping("/ask-naive")
    public Flux<String> askNaive(@RequestParam(defaultValue = "鱼皮的求职指南里都说了写什么？") String message) {
        return assistant.chat("user-1234", message);
    }

}