package com.ai.aiqwen;

import com.ai.aiqwen.service.AiCoderHelperService;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RagTest {
    @Resource
    private AiCoderHelperService aiCoderHelperService;

    @Test
    public void testWithRag(){
        Result<String> chat = aiCoderHelperService.chatWithRag(UserMessage.from("怎么学习Java？有哪些常见的面试题"));
        System.out.println(chat.content());
        System.out.println(chat.sources());
        System.out.println(chat.tokenUsage());
    }

}
