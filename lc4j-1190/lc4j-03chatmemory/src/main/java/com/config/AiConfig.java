package com.config;

import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilder;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class AiConfig {

    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://api.stepfun.com/step_plan/v1";
    private static final String model = "step-3.7-flash";


    @Bean
    public StreamingChatModel streamingChatModel() {
        String apiKey = System.getenv("API_KEY");
        SpringRestClientBuilder springRestClientBuilder = new SpringRestClientBuilder()
                .restClientBuilder(RestClient.builder())
                .readTimeout(Duration.ofMinutes(5));

        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .httpClientBuilder(springRestClientBuilder)
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .maxMessages(3)
                .id("user-123")
                .build();
    }
}
