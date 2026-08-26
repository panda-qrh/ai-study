package com.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://api.stepfun.com/step_plan/v1";
    private static final String model = "step-3.7-flash";


    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }
}