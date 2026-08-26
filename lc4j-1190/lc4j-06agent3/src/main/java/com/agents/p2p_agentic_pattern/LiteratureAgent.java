package com.agents.p2p_agentic_pattern;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface LiteratureAgent {

    @SystemMessage("搜索给定主题的科学文献，并返回研究结果摘要。")
    @UserMessage("""
            你是一名科学文献检索员。
            你的任务是找到用户提供的关于该主题的相关科学论文并对其进行总结。
            使用提供的工具搜索科学论文并返回您的发现摘要。
            主题是：｛｛topic｝｝
            """)
    @Agent("搜索特定主题的科学文献")
    String searchLiterature(@V("topic") String topic);
}