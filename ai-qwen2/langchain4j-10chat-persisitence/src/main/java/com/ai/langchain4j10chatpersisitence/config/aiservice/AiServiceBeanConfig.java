package com.ai.langchain4j10chatpersisitence.config.aiservice;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import jakarta.annotation.Resource;
import com.ai.langchain4j10chatpersisitence.service.aiservice.AiAssistant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceBeanConfig {
    @Resource
    private StreamingChatModel streamingChatModel;
//    @Resource
//    private RedisChatMemoryStore redisChatMemoryStore;

    @Bean
    public AiAssistant aiAssistant() {
        return AiServices.builder(AiAssistant.class)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20)
                        .chatMemoryStore(new InMemoryChatMemoryStore()) //redisChatMemoryStore
                        .build()
                )
                .build();
    }
}
