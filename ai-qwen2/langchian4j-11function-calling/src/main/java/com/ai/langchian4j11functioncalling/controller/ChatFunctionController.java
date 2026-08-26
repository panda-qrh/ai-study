package com.ai.langchian4j11functioncalling.controller;

import com.ai.langchian4j11functioncalling.service.FunctionAssistant;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController

public class ChatFunctionController {

    @Resource
    private FunctionAssistant functionAssistant;
 @Resource
    private FunctionAssistant functionAssistant2;

    @GetMapping("/chat/function/test1")
    public String test1(){
        String chat=functionAssistant.chat("开张发票，公司：尚硅谷教育科技有限公司 税号atguigu533 金额：98992.55");
        System.out.println(chat);
        return "success :"+ LocalDateTime.now()+"\t"+chat;
    }

    @GetMapping("/chat/function/test2/{prompt}")
    public String test2(@PathVariable String prompt){
        String chat=functionAssistant2.chat(prompt);
        System.out.println(chat);
        return "success :"+ LocalDateTime.now()+"\t"+chat;
    }
}
