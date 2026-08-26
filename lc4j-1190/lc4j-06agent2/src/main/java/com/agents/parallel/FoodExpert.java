package com.agents.parallel;

import com.config.AiConfig;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

import java.util.List;

public interface FoodExpert {

    @UserMessage("""
        你是一个出色的晚间计划者。 
        提出一个列表，其中包含3顿符合给定情绪的餐食。 
        心情是{{mood}}。 
        对于每一餐，只需说出餐名即可。 
        提供一个包含3个项目的列表，不要其他内容。
        """)
    @Agent(outputKey = "meals")
    List<String> findMeal(@V("mood") String mood);

    @ChatModelSupplier
    static ChatModel chatModel(){
        return  new AiConfig().chatModel();
    }
}
