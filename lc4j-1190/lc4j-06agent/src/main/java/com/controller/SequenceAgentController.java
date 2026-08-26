package com.controller;

import com.agents.sequence.CreativeWriter;
import com.agents.sequence.NovelCreator;
import dev.langchain4j.agentic.UntypedAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SequenceAgentController {
    @Resource
    private CreativeWriter  creativeWriter;

    @Resource
    private UntypedAgent untypedNovelCreator;

    @Resource
    private NovelCreator novelCreator;

    @GetMapping("/writer")
    public String writer(String topic){
        return creativeWriter.generateStory(topic);
    }

    @GetMapping("/novel")
    public String novel(){
       return  novelCreator.createNovel("龙与巫师","18岁至25岁的年轻人","奇幻");
    }

    @GetMapping("/untyped")
    public String untypedNovelCreator(){
        Map<String, Object> input = Map.of(
                "topic", "龙与巫师",
                "style", "奇幻",
                "audience", "18岁至25岁的年轻人"
        );
        return (String)untypedNovelCreator.invoke(input);
    }

}