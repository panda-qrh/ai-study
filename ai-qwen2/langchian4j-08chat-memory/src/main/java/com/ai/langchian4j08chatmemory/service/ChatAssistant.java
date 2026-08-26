package com.ai.langchian4j08chatmemory.service;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface ChatAssistant {
    String chat(String prompt);

    Flux<String> chatFlux(String prompt);
}
