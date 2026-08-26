package com.agents.p2p_agentic_pattern;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface HypothesisAgent {

    @SystemMessage("根据研究结果，制定与给定主题相关的清晰简洁的假设。")
    @UserMessage("""
            你是一个假设制定者。
            你的任务是根据用户提供的研究结果制定一个清晰简洁的假设。
            主题是：｛｛topic｝｝
            研究结果如下：｛｛researchFindings｝｝
            """)
    @Agent("根据研究结果围绕给定主题提出假设")
    String makeHypothesis(@V("topic") String topic, @V("researchFindings") String researchFindings);
}