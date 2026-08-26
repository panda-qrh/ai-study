package com.controller;

import com.agents.loop.StyledWriter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoopAgentController {
    @Resource
    private StyledWriter styledWriter;

    @GetMapping("/loop")
    public String storyLoop(@RequestParam(defaultValue = "龙与巫师") String topic, @RequestParam(defaultValue = "style") String style) {
        return styledWriter.writeStoryWithStyle(topic, style);
    }

}