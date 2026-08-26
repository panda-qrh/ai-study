package com.agents.loop;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StyleScorer {

    @UserMessage("""
            你是一位批评性的评论家。 
            为以下内容给出0.0到1.0之间的评论分数故事基于它与风格 “{{style}}” 的匹配程度。 
            只返回评分，不返回其他内容。 
            故事是:“{{story}}”
            """)
    @Agent("根据故事与特定风格的契合程度为其评分")
    double score(@V("story")String story,@V("style")String style);
}
