package com.ai01simple;

import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Assistant {
    public String chat(String message);

    @SystemMessage(value = "你是我的好朋友，你使用俚语跟我说话")
    public String chatFriend(String message);

    /**
     * 携带额外参数ChatRequestParameters，必须给message添加@UserMessage
     * @param message
     * @param chatRequestParameters
     * @return
     */
    public String chatWithParam(@UserMessage String message, ChatRequestParameters chatRequestParameters);
}
