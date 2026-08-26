package com.ai.study.controller;

import com.ai.study.tool.CustomTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ToolCallingController {
    @Resource
    private ChatModel chatModel;
    @Resource
    private ChatClient chatClient;

    @GetMapping("/tool/no")
    public Flux<String> noToolCalling() {
        return chatModel.stream("你是谁？现在多少点了？");
    }


    @GetMapping("/tool")
    public Flux<String> toolCalling() {
        ToolCallback[] tools = ToolCallbacks.from(new CustomTools());

        ToolCallingChatOptions chatOptions = DefaultToolCallingChatOptions.builder().toolCallbacks(tools).build();

        Prompt prompt = new Prompt("你是谁？现在几点了？", chatOptions);
        return chatModel.stream(prompt).mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }

    @GetMapping("/tool/client2")
    public Flux<String> toolClient() {

        //方式一、通过编码形式使用tool calling功能，通过prompt()传入
        //region
//        ToolCallback[] tools = ToolCallbacks.from(new CustomTools());
//        ToolCallingChatOptions chatOptions = DefaultToolCallingChatOptions.builder().toolCallbacks(tools).build();
//        Prompt prompt = new Prompt("你是谁？现在几点了？", chatOptions);
//        return chatClient.prompt(prompt)
//                .stream()
//                .content();
        //endregion


        //方式二、通过options()传入tool calling功能
        //region
//        ToolCallback[] tools = ToolCallbacks.from(new CustomTools());
//        return chatClient.prompt()
//                .options(DefaultToolCallingChatOptions.builder().toolCallbacks(tools).build())
//                .user("你是谁？现在几点了？")
//                .stream()
//                .content();
        //endregion

        //方式三、在ChatClient Bean中直接将ChatOptions模型换成DefaultToolCallingChatOptions
        return chatClient.prompt()
                .user("你是谁？现在几点了？")
                .stream()
                .content();
    }

}
