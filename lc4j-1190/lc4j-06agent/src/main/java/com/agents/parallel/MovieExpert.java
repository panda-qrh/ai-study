package com.agents.parallel;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;
public interface MovieExpert {

    @UserMessage("""
        你是一个出色的晚间计划者。 
        提出一个包含3部符合给定情绪的电影的清单。 
        心情是{{mood}}。 
        提供一个包含3个项目的列表，不要其他内容。
        """)
    @Agent
    List<String> findMovie(@V("mood") String mood);
}