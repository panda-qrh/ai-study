package com.agents.condition;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface MedicalExpert {

    @UserMessage("""
        你是医疗专家。
        从医疗角度分析以下用户请求，并提供最佳答案。
        用户请求为{{request}}。
        """)
    @Agent("一个医疗专家")
    String medical(@V("request") String request);
}