package com.controller;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

@RestController
public class ChatMemoryController {
    @Resource
    private StreamingChatModel streamingChatModel;

    @Resource
    private ChatMemory chatMemory;

    @GetMapping("/memory")
    public Flux<String> chatMemory(@RequestParam String message) {
        return Flux.push(emitter -> {
            chatMemory.add(new UserMessage(message));

            ArrayList<ChatMessage> messages = new ArrayList<>(chatMemory.messages());

            streamingChatModel.chat(messages, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    if (!emitter.isCancelled()) {
                        emitter.next(partialResponse);
                    }
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    chatMemory.add(completeResponse.aiMessage());
                    if (!emitter.isCancelled()) {
                        emitter.complete();
                    }
                }

                @Override
                public void onError(Throwable error) {
                    if (!emitter.isCancelled()) {
                        emitter.error(error);
                    }
                }
            });

        });
    }
}
