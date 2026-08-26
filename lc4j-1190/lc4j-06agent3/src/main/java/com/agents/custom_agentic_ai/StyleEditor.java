package com.agents.custom_agentic_ai;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StyleEditor {
    @UserMessage("""
            你是一个专业的编辑。
            分析并重写以下故事，使其更符合{{style}}风格，并更具连贯性。
            只返回故事，不要返回其他内容。
            故事是 “{{story}}”。
            """)
    @Agent("编辑故事以更好地适应特定风格")
    String editStory(@V("story") String story, @V("style") String style);


}
