package com.ai.langchain4j03bootintegration.service;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface AiAssistant {
    String chat(String prompt);
}
