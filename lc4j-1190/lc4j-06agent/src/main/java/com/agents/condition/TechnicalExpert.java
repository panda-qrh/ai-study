package com.agents.condition;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface TechnicalExpert {

    @UserMessage("""
        你是技术专家。
        从技术角度分析以下用户请求，并提供最佳答案。
        用户请求为{{request}}。
        """)
    @Agent("一个技术专家")
    String technical(@V("request") String request);
}