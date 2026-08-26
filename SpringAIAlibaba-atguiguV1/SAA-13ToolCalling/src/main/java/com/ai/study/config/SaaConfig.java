package com.ai.study.config;

import com.ai.study.tool.CustomTools;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.DefaultToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://dashscope.aliyuncs.com/compatible-mode/v1";
    private static final String model = "qwen-plus";

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

    /**
     * 包含tool calling功能
     *
     * @param chatModel
     * @return
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultOptions(DefaultToolCallingChatOptions.builder()
                        .model(model)
                        .toolCallbacks(ToolCallbacks.from(new CustomTools()))
                        .build()
                )
                .build();
    }

}
