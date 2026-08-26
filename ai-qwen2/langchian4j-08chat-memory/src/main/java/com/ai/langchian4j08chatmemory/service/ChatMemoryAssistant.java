package com.ai.langchian4j08chatmemory.service;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface ChatMemoryAssistant {
    String chat(@MemoryId String memoryId,@UserMessage String prompt);

    Flux<String> chatFlux(@MemoryId String memoryId,@UserMessage String prompt);
}
