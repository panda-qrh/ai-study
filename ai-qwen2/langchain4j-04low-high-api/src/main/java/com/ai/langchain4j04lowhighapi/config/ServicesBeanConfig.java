package com.ai.langchain4j04lowhighapi.config;

import com.ai.langchain4j04lowhighapi.service.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 原生框架api高阶功能，在这里定义高阶api业务接口
 */
@Configuration
public class ServicesBeanConfig {
    @Resource
    private ChatModel chatModelQwen;

    @Bean
    public ChatAssistant chatAssistant(){
        return AiServices.create(ChatAssistant.class,chatModelQwen);
    }
}
