package com.agents.p2p_agentic_pattern;

import dev.langchain4j.agentic.planner.Action;
import dev.langchain4j.agentic.planner.AgentInstance;
import dev.langchain4j.agentic.planner.InitPlanningContext;
import dev.langchain4j.agentic.planner.Planner;
import dev.langchain4j.agentic.planner.PlanningContext;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.agentic.scope.AgentInvocation;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class P2PPlanner implements Planner {

    private final int maxAgentsInvocations;
    private final BiPredicate<AgenticScope, Integer> exitCondition;

    private int invocationCounter = 0;
    private Map<String, AgentActivator> agentActivators;

    public P2PPlanner(int maxAgentsInvocations, BiPredicate<AgenticScope, Integer> exitCondition) {
        this.maxAgentsInvocations = maxAgentsInvocations;
        this.exitCondition = exitCondition;
    }

    @Override
    public void init(InitPlanningContext initPlanningContext) {
        this.agentActivators = initPlanningContext.subagents().stream()
                .collect(Collectors.toMap(AgentInstance::agentId, AgentActivator::new));
    }

    @Override
    public Action firstAction(PlanningContext planningContext) {
        return nextCallAction(planningContext.agenticScope());
    }

    @Override
    public Action nextAction(PlanningContext planningContext) {
        if (terminated(planningContext.agenticScope())) {
            return done();
        }

        AgentInvocation previous = planningContext.previousAgentInvocation();
        if (previous != null) {
            AgentActivator activator = agentActivators.get(previous.agentId());
            if (activator != null) {
                activator.finishExecution();
                agentActivators.values().forEach(a -> a.onStateChanged(activator.outputKey()));
            }
        }

        return nextCallAction(planningContext.agenticScope());
    }

    private Action nextCallAction(AgenticScope agenticScope) {
        AgentInstance[] agentsToCall = agentActivators.values().stream()
                .filter(a -> a.canActivate(agenticScope))
                .peek(AgentActivator::startExecution)
                .map(AgentActivator::agent)
                .toArray(AgentInstance[]::new);
        invocationCounter += agentsToCall.length;
        return call(agentsToCall);
    }

    private boolean terminated(AgenticScope agenticScope) {
        return invocationCounter > maxAgentsInvocations || exitCondition.test(agenticScope, invocationCounter);
    }
}
