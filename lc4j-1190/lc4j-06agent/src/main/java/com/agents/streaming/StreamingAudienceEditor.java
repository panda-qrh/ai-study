package com.agents.streaming;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.TokenStream;
import reactor.core.publisher.Flux;

public interface StreamingAudienceEditor {
    @UserMessage(value= """
            你是一位专业编辑。
            分析并重写以下故事，以更好地对齐 与{{audience}}的目标受众。
            只返回故事，不要返回其他内容。
            故事是{{story}}
            """)
    @Agent(value="编辑故事以更好地适应特定受众")
    public Flux<String> editStory(@V("story") Flux<String> story, @V("audience")String audience);


}
