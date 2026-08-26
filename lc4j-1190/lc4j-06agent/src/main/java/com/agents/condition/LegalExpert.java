package com.agents.condition;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface LegalExpert {

    @UserMessage("""
        你是法律专家。
        从法律角度分析以下用户请求，并提供最佳答案。
        用户请求为{{request}}。
        """)
    @Agent("一个法律专家")
    String legal(@V("request") String request);
}