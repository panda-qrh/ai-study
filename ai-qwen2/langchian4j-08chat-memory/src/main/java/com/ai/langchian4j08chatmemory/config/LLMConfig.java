package com.ai.langchian4j08chatmemory.config;

import com.ai.langchian4j08chatmemory.service.ChatMemoryAssistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.UUID;

@Configuration
@Slf4j
public class LLMConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String modelName = "qwen-plus";
    private static final String embeddingModelName = "xxxx";

    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .logRequests(true)
                .logResponses(true)
                .listeners(List.of(new ChatModelListener() {//监听器，不想另写一个类了，直接在这里写
                    @Override
                    public void onRequest(ChatModelRequestContext requestContext) {
                        String traceId = UUID.randomUUID().toString();
                        requestContext.attributes().put("TraceId", traceId);
                        log.info("请求的TraceId：{}", traceId);
                    }

                    @Override
                    public void onResponse(ChatModelResponseContext responseContext) {
                        String traceIdValue = (String) responseContext.attributes().get("TraceId");
                        log.info("响应的TraceId：{}", traceIdValue);
                    }

                    @Override
                    public void onError(ChatModelErrorContext errorContext) {
                        log.error("出现了错误，TraceId：{}，错误在：{}", errorContext);
                    }
                }))
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

}
