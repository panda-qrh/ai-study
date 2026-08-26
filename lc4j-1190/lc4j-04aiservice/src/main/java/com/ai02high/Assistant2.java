package com.ai02high;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "chatModel",
        streamingChatModel = "streamingChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = {"tools"})
public interface Assistant2 {

    Flux<String> chat(String message);

    Flux<String> chat(@UserMessage String message, @UserMessage ImageContent image);

    Flux<String> chatMemory(@MemoryId String memoryId, @UserMessage String message);
}
