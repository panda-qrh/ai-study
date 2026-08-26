package com.ai.langchian4j07chatstream.controller;

import com.ai.langchian4j07chatstream.service.ChatAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 原生框架，使用高阶api
 */
@RestController
public class StreamingChatController {
    @Resource
    private ChatAssistant chatAssistant;
    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping("/lc4j/streaming")
    public Flux<String> chatFlux(@RequestParam(value = "prompt", defaultValue = "who are you?") String prompt) {
        System.out.println("--come in chat--");
        Flux<String> res = Flux.create(emitter -> {
            streamingChatModel.chat(prompt, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable error) {
                    emitter.error(error);
                }
            });
        });
        return res;

    }

    @GetMapping("/lc4j/streaming2")
    public Flux<String> chatFlux2(@RequestParam(value = "prompt", defaultValue = "who are you?") String prompt) {
        Flux<String> res = chatAssistant.chatFlux(prompt);
        return res;

    }
}
