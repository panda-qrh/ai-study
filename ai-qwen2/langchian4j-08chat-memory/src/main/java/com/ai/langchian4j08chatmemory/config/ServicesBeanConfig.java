package com.ai.langchian4j08chatmemory.config;

import com.ai.langchian4j08chatmemory.service.ChatAssistant;
import com.ai.langchian4j08chatmemory.service.ChatMemoryAssistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

//    @Bean
//    public ChatAssistant chatAssistant(){
//        return AiServices.create(ChatAssistant.class,chatModelQwen);
//    }


    /**
     * 采用流式chat model
     *
     * @return
     */
    @Bean
    public ChatAssistant chatAssistant() {
        return AiServices.create(ChatAssistant.class, streamingChatModel);
    }


    @Bean
    public ChatMemoryAssistant chatMessageWindowChatMemory(ChatModel chatModel) {
        return AiServices.builder(ChatMemoryAssistant.class)
                .streamingChatModel(streamingChatModel)
                // 注意每个memoryId对应创建一个ChatMemory
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(3)
                        .build())
                .build();
    }

}
