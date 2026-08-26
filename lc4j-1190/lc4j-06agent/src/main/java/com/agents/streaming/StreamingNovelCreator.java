package com.agents.streaming;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;
import dev.langchain4j.service.TokenStream;
import reactor.core.publisher.Flux;

public interface StreamingNovelCreator {


    @Agent
    Flux<String> createNovel(@V("topic") String topic, @V("audience") String audience, @V("style") String style);
}