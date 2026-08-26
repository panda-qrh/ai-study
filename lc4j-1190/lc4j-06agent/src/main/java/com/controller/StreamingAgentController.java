package com.controller;

import com.agents.streaming.StreamingNovelCreator;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class StreamingAgentController {

    @Resource
    private UntypedAgent untypedStreamingNovelCreator;

    @Resource
    private StreamingNovelCreator streamingNovelCreator;

    @GetMapping(value = "/novel/stream")
    public Flux<String> novel() {
       return streamingNovelCreator.createNovel("龙与巫师", "18岁至25岁的年轻人", "奇幻");
    }



    @GetMapping("/untyped/stream")
    public Flux<?> untypedNovelCreator() {
        Map<String, Object> input = Map.of(
                "topic", "龙与巫师",
                "style", "奇幻",
                "audience", "18岁至25岁的年轻人"
        );
        return (Flux<?>) untypedStreamingNovelCreator.invoke(input);

    }
}
