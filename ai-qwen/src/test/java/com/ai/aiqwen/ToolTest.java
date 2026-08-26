package com.ai.aiqwen;

import com.ai.aiqwen.service.AiCoderHelperService;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class ToolTest {
    @Resource
    private AiCoderHelperService aiCoderHelperService;

    @Test
    public void testWithTool() {
        String chat = aiCoderHelperService.chat(UserMessage.from("有哪些常见的计算机网络面试题？"));
        System.out.println(chat);
    }

    @Test
    public void testAddTool() {
        Result<String> res = aiCoderHelperService.chatWithTool(UserMessage.from("帮我计算12和23的和"));

        System.out.println(res.content());
    }
}
