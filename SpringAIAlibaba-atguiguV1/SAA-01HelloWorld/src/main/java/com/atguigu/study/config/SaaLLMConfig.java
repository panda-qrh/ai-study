package com.atguigu.study.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther zzyybs@126.com
 * @create 2025-07-22 0:51
 */
@Configuration
public class SaaLLMConfig {
    private static final String apiKey=System.getenv("API_KEY");
    private static final String url="https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String model="qwen-plus";
    @Bean
    public ChatModel chatModel() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder()
                        .apiKey(apiKey)
                        .baseUrl(url)
                        .build())
                .defaultOptions(DashScopeChatOptions.builder()
                        .withModel(model)
                        .build())
                .build();
    }

}
