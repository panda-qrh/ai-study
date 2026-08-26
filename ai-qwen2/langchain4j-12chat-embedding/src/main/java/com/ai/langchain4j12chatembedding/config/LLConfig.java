package com.ai.langchain4j12chatembedding.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLConfig {

    private static final String apiKey = System.getenv("API_KEY");
    private static final String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String modelName = "qwen-plus";


    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();

    }

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

/**
 * 向量化模型
 */
@Bean
    public EmbeddingModel embeddingModel(){
    return OpenAiEmbeddingModel.builder()
            .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
            .apiKey("sk-d91bc49d603a4e23b307d4f1abe326ed")
            .modelName("text-embedding-v4")
            .build();
}

}
