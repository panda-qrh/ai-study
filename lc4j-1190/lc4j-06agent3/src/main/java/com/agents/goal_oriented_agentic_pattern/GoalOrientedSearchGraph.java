package com.agents.goal_oriented_agentic_pattern;

import dev.langchain4j.agentic.planner.AgentInstance;
import dev.langchain4j.agentic.planner.Action;
import dev.langchain4j.agentic.planner.InitPlanningContext;
import dev.langchain4j.agentic.planner.PlanningContext;

import java.util.*;

/**
 * 根据图片中的依赖图构建搜索图，使用 BFS 计算从初始状态到目标的最短路径。
 *
 * <p>依赖关系（从图片）：
 * <pre>
 * prompt → PersonExtractor ──person──→ HoroscopeGenerator ──horoscope──→ Writer
 *                                       ├──sign──→ SignExtractor ──sign──→ │
 *                                       │                             │
 *                                       └──person→ StoryFinder ──story──→ │
 * </pre>
 */
class GoalOrientedSearchGraph {

    /** 每个节点的定义：产出什么 key，依赖什么 key */
    record Node(String outputKey, Set<String> requiredKeys) {
        boolean canExecute(Set<String> availableKeys) {
            return availableKeys.containsAll(requiredKeys);
        }
    }

    /** outputKey → Node 定义 */
    private final Map<String, Node> nodeMap = new LinkedHashMap<>();

    /** outputKey → AgentInstance */
    private final Map<String, AgentInstance> agentMap = new HashMap<>();

    GoalOrientedSearchGraph(List<AgentInstance> subagents) {
        buildGraph(subagents);
    }

    /**
     * 根据图片的依赖关系构建图。
     * <p>
     * 每个节点的 requiredKeys 对应 agent 方法的 @V 参数名，
     * outputKey 对应 agent 的 outputKey 配置。
     */
    private void buildGraph(List<AgentInstance> subagents) {
        // 建立 outputKey → AgentInstance 索引
        for (AgentInstance agent : subagents) {
            agentMap.put(agent.outputKey(), agent);
        }

        // PersonExtractor: 输入 prompt → 产出 person
        defineNode("person", Set.of("prompt"));
        // SignExtractor: 输入 prompt → 产出 sign
        defineNode("sign", Set.of("prompt"));
        // HoroscopeGenerator: 输入 person + sign → 产出 horoscope
        defineNode("horoscope", Set.of("person", "sign"));
        // StoryFinder: 输入 person + horoscope → 产出 story
        defineNode("story", Set.of("person", "horoscope"));
        // Writer: 输入 person + horoscope + story → 产出 writeup
        defineNode("writeup", Set.of("person", "horoscope", "story"));
    }

    private void defineNode(String outputKey, Set<String> requiredKeys) {
        if (!agentMap.containsKey(outputKey)) {
            throw new IllegalArgumentException("没发现Agent带有这个outputKey: " + outputKey);
        }
        nodeMap.put(outputKey, new Node(outputKey, requiredKeys));
    }


    List<AgentInstance> search(Set<String> availableKeys, String goal) {
        if (availableKeys.contains(goal)) {
            return List.of(); // goal 已存在，无需执行
        }

        // BFS 队列：每个元素是 (当前状态key集合, 已执行的路径)
        Queue<SearchState> queue = new LinkedList<>();
        queue.offer(new SearchState(new HashSet<>(availableKeys), List.of()));

        // 避免重复访问同一状态
        Set<String> visited = new HashSet<>();
        visited.add(stateKey(availableKeys));

        while (!queue.isEmpty()) {
            SearchState current = queue.poll();

            // 遍历所有当前可执行的节点
            List<AgentInstance> candidates = findExecutableAgents(current.state(), current.path());
            for (AgentInstance agent : candidates) {
                String outputKey = agent.outputKey();
                Set<String> newState = new HashSet<>(current.state());
                newState.add(outputKey);
                List<AgentInstance> newPath = new ArrayList<>(current.path());
                newPath.add(agent);

                // 到达目标
                if (newState.contains(goal)) {
                    return newPath;
                }

                // 入队继续搜索
                String newStateKey = stateKey(newState);
                if (!visited.contains(newStateKey)) {
                    visited.add(newStateKey);
                    queue.offer(new SearchState(newState, newPath));
                }
            }
        }

        throw new IllegalStateException(
                "No path found from state " + availableKeys + " to goal: " + goal);
    }

    /**
     * 找到当前状态下可执行且未执行过的 agent 列表。
     */
    private List<AgentInstance> findExecutableAgents(Set<String> state, List<AgentInstance> executedPath) {
        List<AgentInstance> candidates = new ArrayList<>();
        Set<String> executedKeys = new HashSet<>();
        for (AgentInstance a : executedPath) {
            executedKeys.add(a.outputKey());
        }

        for (Map.Entry<String, Node> entry : nodeMap.entrySet()) {
            String outputKey = entry.getKey();
            if (executedKeys.contains(outputKey)) continue;
            Node node = entry.getValue();
            if (node.canExecute(state)) {
                candidates.add(agentMap.get(outputKey));
            }
        }
        return candidates;
    }

    /** 将 state 的 key 排序后拼接为字符串，用于 visited set 去重 */
    private String stateKey(Set<String> state) {
        List<String> sorted = new ArrayList<>(state);
        Collections.sort(sorted);
        return String.join(",", sorted);
    }

    /** BFS 搜索状态 */
    record SearchState(Set<String> state, List<AgentInstance> path) {
    }
}
