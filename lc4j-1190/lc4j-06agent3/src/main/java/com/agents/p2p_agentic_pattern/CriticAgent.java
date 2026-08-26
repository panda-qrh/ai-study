package com.agents.p2p_agentic_pattern;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CriticAgent {

    @SystemMessage("批判性地评估与指定主题相关的给定假设。提供建设性的反馈，并在必要时提出改进建议。")
    @UserMessage("""
            你是一个批判性的评估Agent。
            你的任务是批判性地评估用户提供的与指定主题相关的假设。
            提供建设性的反馈，并在必要时提出改进建议。
            如果需要，您还可以使用提供的工具进行额外的研究，以验证或反驳假设。
            主题是：｛｛topic｝｝
            假设是：{{hypothesis}}
            """)
    @Agent("批判性地评估与给定主题相关的假设")
    String criticHypothesis(@V("topic") String topic, @V("hypothesis") String hypothesis);
}