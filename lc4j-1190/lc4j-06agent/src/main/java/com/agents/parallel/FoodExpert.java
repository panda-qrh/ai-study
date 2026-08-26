package com.agents.parallel;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface FoodExpert {

    @UserMessage("""
        你是一个出色的晚间计划者。 
        提出一个列表，其中包含3顿符合给定情绪的餐食。 
        心情是{{mood}}。 
        对于每一餐，只需说出餐名即可。 
        提供一个包含3个项目的列表，不要其他内容。
        """)
    @Agent
    List<String> findMeal(@V("mood") String mood);

}
