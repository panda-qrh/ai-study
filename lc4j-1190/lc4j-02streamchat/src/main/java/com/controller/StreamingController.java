package com.controller;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamingController {
    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping("/stream")
    public void stream(@RequestParam(defaultValue = "讲一个冷笑话，300字以内") String message) {
         streamingChatModel.chat(message,new StreamingChatResponseHandler(){
             public void onPartialResponse(String partialResponse) {
                 System.out.println("onPartialResponse: " + partialResponse);
             }
             public void onPartialThinking(PartialThinking partialThinking) {
                 System.out.println("onPartialThinking: " + partialThinking);
             }
             public void onPartialToolCall(PartialToolCall partialToolCall) {
                 System.out.println("onPartialToolCall: " + partialToolCall);
             }
             public void onCompleteToolCall(CompleteToolCall completeToolCall) {
                 System.out.println("onCompleteToolCall: " + completeToolCall);
             }
             public void onCompleteResponse(ChatResponse completeResponse) {
                 System.out.println("onCompleteResponse: " + completeResponse);
             }
             public void onError(Throwable error) {
                 error.printStackTrace();
             }
        });
    }

    @GetMapping(value = "/stream2")
    public Flux<String> stream2(@RequestParam(defaultValue = "讲一个冷笑话，300字以内") String message) {
        return Flux.create(emitter->{
            streamingChatModel.chat(message,new StreamingChatResponseHandler(){
                public void onPartialResponse(String partialResponse) {
                   emitter.next(partialResponse);
                }
                public void onCompleteResponse(ChatResponse completeResponse) {
                    System.out.println("===completeResponse===\n"+completeResponse);
                    emitter.complete();
                }
                public void onError(Throwable error) {
                    emitter.error(error);
                }
            });

        });

    }
}