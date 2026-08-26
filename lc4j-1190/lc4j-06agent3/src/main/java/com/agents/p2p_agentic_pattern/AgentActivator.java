package com.agents.p2p_agentic_pattern;

import dev.langchain4j.agentic.planner.AgentInstance;
import dev.langchain4j.agentic.scope.AgenticScope;

import java.util.HashSet;
import java.util.Set;

/**
 * 管理 P2P 模式中每个 agent 的激活状态。
 */
class AgentActivator {

    private final AgentInstance agent;
    private final Set<String> requiredKeys;
    private boolean executed = false;
    private boolean executing = false;

    AgentActivator(AgentInstance agent) {
        this.agent = agent;
        this.requiredKeys = extractRequiredKeys(agent);
    }

    /**
     * 从 AgentInstance.arguments() 中提取 @V 参数名作为所需的 key。
     */
    private static Set<String> extractRequiredKeys(AgentInstance agent) {
        Set<String> keys = new HashSet<>();
        for (dev.langchain4j.agentic.planner.AgentArgument arg : agent.arguments()) {
            keys.add(arg.name());
        }
        return keys;
    }

    /**
     * 检查该 agent 是否可以执行：未执行过 + 所有依赖 key 都在 scope 中。
     */
    boolean canActivate(AgenticScope scope) {
        if (executed || executing) {
            return false;
        }
        for (String key : requiredKeys) {
            if (!scope.hasState(key)) {
                return false;
            }
        }
        return true;
    }

    void startExecution() {
        this.executing = true;
    }

    void finishExecution() {
        this.executed = true;
        this.executing = false;
    }

    void onStateChanged(String outputKey) {
        // 状态已变化，下次 canActivate 会自动重新检查
    }

    AgentInstance agent() {
        return agent;
    }

    String agentId() {
        return agent.agentId();
    }

    String outputKey() {
        return agent.outputKey();
    }
}
