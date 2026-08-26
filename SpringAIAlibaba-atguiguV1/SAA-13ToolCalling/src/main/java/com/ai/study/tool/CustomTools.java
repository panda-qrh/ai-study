package com.ai.study.tool;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

public class CustomTools {

    @Tool(name="tool-获取当前时间", description = "获取当前时间")
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
