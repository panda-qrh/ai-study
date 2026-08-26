package com.agents.sequence;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * {@link com.config.AgentConfig}
 */
public interface CreativeWriter {
    @UserMessage("""
            你是一个富有创造力的作家。 
            生成一个故事的草稿，不超过围绕给定主题的3个长句子。
            只返回故事，不要返回其他内容。 
            主题是{{topic}}。
            """)
    @Agent("根据给定主题生成故事")
    String generateStory(@V("topic") String topic);
}
