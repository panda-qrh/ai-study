package com.ai.langchain4j10chatpersisitence.service.aiservice;

import reactor.core.publisher.Flux;

public interface AiAssistant {
    public Flux<String> chat(String prompt);
}
