package com.controller;

import dev.langchain4j.agentic.UntypedAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PlannerController {
    @Resource
    private UntypedAgent novelCreator;

    @GetMapping("/create-novel")
    public String createNovel() {
        Map<String, Object> map = Map.of(
                "topic", "小黄人大眼萌",
                "audience", "全年龄段人群",
                "style", "喜剧、搞笑"
        );
        return (String) novelCreator.invoke(map);
    }


}