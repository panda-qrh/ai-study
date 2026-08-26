package com.agents.p2p_agentic_pattern;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ScorerAgent {

    @SystemMessage("根据提供的评论，对给定主题的假设进行评分。")
    @UserMessage("""
            你是一名评分Agent。
            你的任务是根据提供的评论，对用户提供的与指定主题相关的假设进行评分。
            在0.0到1.0的范围内对提供的假设进行评分，其中0.0表示假设完全无效，1.0表示假设完全有效。
            主题是：｛｛topic｝｝
            假设是：{{hypothesis}}
            评论是：{{critique}}
            """)
    @Agent("根据给定的主题和评论对假设进行评分")
    double scoreHypothesis(@V("topic") String topic, @V("hypothesis") String hypothesis, @V("critique") String critique);
}