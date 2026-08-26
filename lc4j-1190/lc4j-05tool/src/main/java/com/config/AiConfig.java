package com.config;

import com.aiservices.Assistant;
import com.tools.MyTools;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AiConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://api.stepfun.com/step_plan/v1";
    private static final String model = "step-3.7-flash";

    @Bean
    public StreamingChatModel streamingChatModel(){
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }

    @Bean
    public ChatModel chatModel(){
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }

    @Bean
    public Assistant assistant(StreamingChatModel streamingChatModel, ChatModel chatModel){
        return AiServices.builder(Assistant.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .tools(new MyTools()) //单纯的配置tools有概率不使用tool，这不代表它不能使用tool
                .build();
    }
}
