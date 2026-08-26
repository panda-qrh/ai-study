package com.agents.condition;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.V;

public interface ExpertRouterAgent {

    @Agent
    String ask(@V("request") String request);

}