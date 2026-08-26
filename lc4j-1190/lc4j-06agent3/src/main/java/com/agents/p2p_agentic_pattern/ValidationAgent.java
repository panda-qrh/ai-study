package com.agents.p2p_agentic_pattern;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ValidationAgent {

    @SystemMessage("根据所提供的评论，验证所提供的关于给定主题的假设。")
    @UserMessage("""
            你是验证代理。
            你的任务是根据提供的评论验证用户提供的与指定主题相关的假设。
            验证所提供的假设，要么确认它，要么根据评论重新制定不同的假设。
            主题是：｛｛topic｝｝
            假设是：{{hypothesis}}
            评论是：{{critique}}
            """)
    @Agent("根据给定的主题和评论验证假设")
    String validateHypothesis(@V("topic") String topic, @V("hypothesis") String hypothesis, @V("critique") String critique);
}
