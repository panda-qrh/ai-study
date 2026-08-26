package com.ai.langchain4j04lowhighapi.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Configuration
@Slf4j
public class LLMConfig {

    private static final String apiKey = System.getenv("API_KEY");
    private static final String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String modelName = "qwen-plus";

    @Bean
    public ChatModel chatModelQwen() {
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
                        log.info("请求的TraceId：{}",traceId);
                    }
                    @Override
                    public void onResponse(ChatModelResponseContext responseContext) {
                        String traceIdValue = (String)responseContext.attributes().get("TraceId");
                        log.info("响应的TraceId：{}",traceIdValue);
                    }
                    @Override
                    public void onError(ChatModelErrorContext errorContext) {
                        log.error("出现了错误，TraceId：{}，错误在：{}",errorContext);
                    }
                }))
                .build();
    }
}
