package com.agents.p2p_agentic_pattern;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ResearchAgent {

    @Agent("对给定主题进行研究")
    String research(@V("topic") String topic);
}