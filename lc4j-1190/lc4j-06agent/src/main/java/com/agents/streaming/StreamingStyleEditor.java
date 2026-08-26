package com.agents.streaming;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

public interface StreamingStyleEditor {
    @UserMessage("""
            你是一个专业的编辑。
            分析并重写以下故事，使其更符合{{style}}风格，并更具连贯性。
            只返回故事，不要返回其他内容。
            故事是 “{{story}}”。
            """)
    @Agent("编辑故事以更好地适应特定风格")
    Flux<String> editStory(@V("story") Flux<String> story, @V("style") String style);


}
