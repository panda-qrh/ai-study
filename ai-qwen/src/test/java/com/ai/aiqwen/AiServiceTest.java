package com.ai.aiqwen;

import com.ai.aiqwen.service.AiCoderHelperService;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiServiceTest {
    @Resource
    private AiCoderHelperService aiCoderHelperService;

    /**
     * 简单实用ai service
     */
    @Test
    public void  testAiService() {
        String chat = aiCoderHelperService.chat(UserMessage.from("你好，我是程序员辉！"));
        System.out.println(chat);
    }

    /**
     * 使用ai 服务+会话记忆
     */
    @Test
    public void  testAiServiceWithMemory() {
        String chat = aiCoderHelperService.chat(UserMessage.from("你好，我是程序员辉！"));
        System.out.println(chat);

        chat = aiCoderHelperService.chat(UserMessage.from("请问，作为一个即将找工作的我，如何做准备？"));
        System.out.println(chat);

        chat = aiCoderHelperService.chat(UserMessage.from("关于简历，该如何准备和制作，请给点建议。"));
        System.out.println(chat);

        chat = aiCoderHelperService.chat(UserMessage.from("如何在简历中突出自己的亮点，我会java那一套，现在也会langchain4j和spring ai alibaba这两个ai框架"));
        System.out.println(chat);

        chat = aiCoderHelperService.chat(UserMessage.from("喂喂喂~，你还知道我是谁吗？"));
        System.out.println(chat);
    }

}
