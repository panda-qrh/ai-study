package com.controller;

import dev.langchain4j.agentic.UntypedAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class P2PController {
    @Resource
    private UntypedAgent researchAgent;

    @GetMapping("/p2p")
    public String research() {
        Map<String, Object> input = Map.of("topic", "黑洞");
        return (String)researchAgent.invoke(input);
    }


}