package com.controller;

import com.aiservices.Assistant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ToolChoice;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.response.*;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ToolController {

    @Resource
    private Assistant assistant;

    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "1+3=？") String message) {

        ChatRequest chatRequest = ChatRequest.builder()
                .messages(new UserMessage("张三", message))
                .toolSpecifications(
                        ToolSpecification.builder()
                                .name("sum2numbers")
                                .description("给两个数，求这两个数的和")
                                .parameters(JsonObjectSchema.builder()
                                        .addNumberProperty("a", "被加数")
                                        .addNumberProperty("b", "加数")
                                        .build()
                                )
                                .build(),
                        ToolSpecification.builder()
                                .name("multiply2numbers")
                                .description("给定两个数，求这两个数的乘积")
                                .parameters(JsonObjectSchema.builder()
                                        .addNumberProperty("a", "被乘数")
                                        .addNumberProperty("b", "被乘数")
                                        .build()
                                )
                                .build()
                )
                .toolChoice(ToolChoice.AUTO)
                .build();

        // 第一次请求：模型可能会返回 ToolExecutionRequest 而不是文字
        ChatResponse response = chatModel.chat(chatRequest);

        // 如果模型决定调用工具（aiMessage.text 为 null）
        if (response.aiMessage().hasToolExecutionRequests()) {
            System.out.println("模型请求调用工具，开始手动执行工具...");
            // 取第一个工具调用请求（当前场景一次只调用一个工具）
            var toolRequest = response.aiMessage().toolExecutionRequests().get(0);
            String toolResult = executeTool(toolRequest.name(), toolRequest.arguments());
            // 构造工具执行结果消息，发回模型让它生成最终文字回答
            ToolExecutionResultMessage toolResultMessage = ToolExecutionResultMessage.from(
                    toolRequest.id(),
                    toolRequest.name(),
                    toolResult
            );
            // 第二次请求：带上工具执行结果，让模型生成最终回答
            ChatRequest followUp = ChatRequest.builder()
                    .messages(
                            new UserMessage("张三", message),
                            response.aiMessage(),       // 模型的工具调用请求
                            toolResultMessage            // 工具执行结果
                    )
                    .build();

            response = chatModel.chat(followUp);
        }

        System.out.println("最终回答：" + response.aiMessage().text());
        return response.aiMessage().text();
    }


    @GetMapping("/ack")
    public Flux<String> ack(@RequestParam(defaultValue = "1+3=？") String message) {
        return assistant.chat(message);
    }

    @GetMapping(value = "/resp")
    public String chatResponse(@RequestParam(defaultValue = "1+3=？") String message) {
        ChatResponse response = assistant.chatResponse(message);
        System.out.println(response);
        return response.aiMessage().text();
    }


    /**
     * 根据工具名称和参数，手动执行对应的工具逻辑
     */
    private String executeTool(String toolName, String argumentsJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode args = objectMapper.readTree(argumentsJson);
            BigDecimal a = new BigDecimal(args.get("a").asText());
            BigDecimal b = new BigDecimal(args.get("b").asText());

            return switch (toolName) {
                case "sum2numbers" -> "计算结果：" + a.add(b);
                case "multiply2numbers" -> "计算结果：" + a.multiply(b);
                default -> "未知工具：" + toolName;
            };
        } catch (Exception e) {
            return "工具执行失败：" + e.getMessage();
        }
    }


}