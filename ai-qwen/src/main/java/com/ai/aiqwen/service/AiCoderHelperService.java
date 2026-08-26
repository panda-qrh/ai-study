package com.ai.aiqwen.service;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

//@AiService
public interface AiCoderHelperService {
    @SystemMessage(fromResource = "system-prompt.txt")
    public String chat(UserMessage userMessage);

    public  Result<String>  chatWithTool( UserMessage userMessage);

    @SystemMessage(fromResource = "system-prompt.txt")
    public Result<String> chatWithRag(UserMessage userMessage);
}
