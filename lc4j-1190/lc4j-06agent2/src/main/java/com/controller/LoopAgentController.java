package com.controller;

import com.agents.loop.StyledWriter;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.observability.AgentMonitor;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class LoopAgentController {
    @Resource
    private UntypedAgent untypedStyledWriter;

    @GetMapping("/listen/untyped")
    public String listenUntyped() {
        Map<String, Object> map = Map.of(
                "topic", "龙与巫师",
                "style", "搞怪无厘头"
        );
        String res= (String)untypedStyledWriter.invoke(map);
        return res;
    }

}