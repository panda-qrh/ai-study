package com.config;

import com.ai01simple.Assistant;
import com.ai02high.Assistant2;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
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

    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(url)
                .apiKey(apiKey)
                .modelName(model)
                .build();
    }

    @Bean
    public ChatMemoryProvider  chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .maxMessages(3)
                .build();
    }


    /**
     * {@link com.ai01simple.Assistant}
     *
     * @param chatModel
     * @return
     */
    @Bean
    public Assistant assistant(ChatModel chatModel) {
        return AiServices.create(Assistant.class, chatModel);
    }

    /**
     * 配置bean方式有两种，幺妹使用@AiService注解，{@link Assistant2}； 要么使用显示配置。两者不能同时存在
     * @param streamingChatModel
     * @return
     */
//    @Bean
//    public Assistant2 assistant2(StreamingChatModel streamingChatModel) {
//        return AiServices.create(Assistant2.class, streamingChatModel);
//    }

}