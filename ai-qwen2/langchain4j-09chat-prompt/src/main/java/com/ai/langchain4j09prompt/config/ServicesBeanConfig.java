package com.ai.langchain4j09prompt.config;

import com.ai.langchain4j09prompt.service.ChatAssistant;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
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
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    /**
     * 采用流式chat model
     *
     * @return
     */
    @Bean
    public ChatAssistant chatAssistant() {
        return AiServices.create(ChatAssistant.class, streamingChatModel);
    }



}
