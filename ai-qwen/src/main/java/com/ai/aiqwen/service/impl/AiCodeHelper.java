package com.ai.aiqwen.service.impl;

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiCodeHelper {
    @Resource
    private ChatModel qwenChatModel;

    /**
     * 最基础的使用
     *
     * @param userMessage 用户消息
     * @return ai回答的内容
     */
    public String chat(String userMessage) {
        UserMessage message = UserMessage.from(userMessage);
        ChatResponse chatResponse = qwenChatModel.chat(message);
        return chatResponse.toString();
    }

    /**
     * 多模态消息使用
     *
     * @param userMessage
     * @return
     */
    public String chat(UserMessage userMessage) {
        return qwenChatModel.chat(userMessage).toString();
    }


    /**
     * 带有系统提示词的消息
     *
     * @param userMessage 用户消息
     * @return
     */
    public String chatWithSystemMessage(UserMessage userMessage) {
        String text = FileSystemDocumentLoader.loadDocument("src/main/resources/system-prompt.txt").text();
        SystemMessage systemMessage = SystemMessage.from(text);
        ChatRequest request = ChatRequest.builder()
                .messages(systemMessage,userMessage)
                .build();

        ChatResponse chatResponse = qwenChatModel.chat(request);
        return chatResponse.toString();
    }


}
