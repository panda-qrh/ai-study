package com.aiservices;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface Assistant {
    Flux<String> chat(String message);

    ChatResponse chatResponse(String message);
}
