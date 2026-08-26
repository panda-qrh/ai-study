package com.agents.condition;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CategoryRouter {
    @UserMessage("""
        分析以下用户请求，
        并将其分类为 'legal','medical'或'technical'。 
        如果请求不属于这些类别中的任何一个，则将其归类为 'unknown'。 
        只用其中一个词回复，不要用其他词。 
        用户请求为:“{{request}}”。
        """)
    @Agent(value="给用户的请求进行分类",outputKey = "category")
    RequestCategory classify(@V("request") String request);


}
