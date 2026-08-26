package com.agents.parallel;

import com.config.AiConfig;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.agentic.declarative.RegistryAgent;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import jakarta.annotation.Resource;

import java.util.List;


public interface MovieExpert {

    @UserMessage("""
            你是一个出色的晚间计划者。 
            提出一个包含3部符合给定情绪的电影的清单。 
            心情是{{mood}}。 
            提供一个包含3个项目的列表，不要其他内容。
            """)
    @Agent(outputKey = "movies")
    List<String> findMovie(@V("mood") String mood);

    @ChatModelSupplier
    static ChatModel chatModel() {
        return new AiConfig().chatModel();
    }
}