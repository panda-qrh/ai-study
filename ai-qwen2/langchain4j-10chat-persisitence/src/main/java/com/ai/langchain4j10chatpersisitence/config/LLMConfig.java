package com.ai.langchain4j10chatpersisitence.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String modelName = "qwen-plus";

    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

}
