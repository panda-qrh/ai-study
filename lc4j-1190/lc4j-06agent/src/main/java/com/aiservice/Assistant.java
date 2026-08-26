package com.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "chatModel",
        streamingChatModel = "streamingChatModel",
        chatMemoryProvider = "chatMemoryProvider")
public interface Assistant {

    String chat(@MemoryId String memoryId,@UserMessage String message);

    Flux<String> chatStream(@MemoryId String memoryId,@UserMessage String message);

}
