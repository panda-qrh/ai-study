package com.ai.langchain4j09prompt.service;


import com.ai.langchain4j09prompt.pojo.QuestionPrompt;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

public interface ChatAssistant {
    String chat(String prompt);

    @SystemMessage(value= """
            你是一位专业的中国法律顾问，只回答与中国法律相关的问题。
            输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答中国法律相关的问题。'
            """)
    @UserMessage(value="请回答以下法律问题：{{question}}，字数控制在{{length}}以内。")
    Flux<String> chatFlux(@V("question")String question,@V("length")Integer length);

    @SystemMessage(value= """
            你是一位专业的中国法律顾问，只回答与中国法律相关的问题。
            输出限制：对于其他领域的问题禁止回答，直接返回'抱歉，我只能回答中国法律相关的问题。'
            """)
    Flux<String> chatFlux(QuestionPrompt questionPrompt);
}
