package com.ai.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SAAConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String QWEN_PLUS = "qwen-plus";
    private static final String QWEN_LONG = "qwen-long-latest";
    private static final String QWEN_MAX = "qwen-max";

    @Bean(name = "chatModelQwenPlus")
    @Primary
    public ChatModel chatModelQwenPlus() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder()
                        .apiKey(apiKey)
                        .baseUrl(url)
                        .build())
                .defaultOptions(DashScopeChatOptions.builder()
                        .withModel(QWEN_PLUS)
                        .build())
                .build();
    }


    @Bean(name = "chatModelQwenLong")
    public ChatModel chatModelQwenLong() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder()
                        .apiKey(apiKey)
                        .baseUrl(url)
                        .build())
                .defaultOptions(DashScopeChatOptions.builder()
                        .withModel(QWEN_LONG)
                        .withStream(true)
                        .build())
                .build();
    }

    @Bean(name = "chatModelQwenMax")
    public ChatModel chatModelQwenMax() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder()
                        .apiKey(apiKey)
                        .baseUrl(url)
                        .build())
                .defaultOptions(DashScopeChatOptions.builder()
                        .withModel(QWEN_MAX)
                        .withStream(true)
                        .build())
                .build();
    }
}
