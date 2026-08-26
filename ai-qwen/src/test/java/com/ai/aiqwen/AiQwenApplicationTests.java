package com.ai.aiqwen;

import com.ai.aiqwen.service.impl.AiCodeHelper;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiQwenApplicationTests {

    @Resource
    private AiCodeHelper aiCodeHelper;

//    @Resource
//    private ChatModel qwenChatModel;

    @Test
    void contextLoads() {
        String chatResponse = aiCodeHelper.chat(UserMessage.from("介绍一下雷军"));
        System.out.println(chatResponse);
    }

    @Test
    public void testChatWithMessage() {
        //qwen-long-latest无法分析图片
        UserMessage userMessage = UserMessage.from(
                TextContent.from("描述下面这张图片"),
                ImageContent.from("src/main/resources/images/柯南AI.png")
        );
        String chat = aiCodeHelper.chat(userMessage);
        System.out.println(chat);
    }

    /**
     * 测试 系统提示词
     */
    @Test
    public void testWithSystemMessage() {
        String chat = aiCodeHelper.chatWithSystemMessage(UserMessage.from("你好，我是程序员辉"));
        System.out.println(chat);
    }


}
