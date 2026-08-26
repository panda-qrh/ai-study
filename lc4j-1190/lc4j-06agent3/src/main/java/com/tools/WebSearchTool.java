package com.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

//@Component
public class WebSearchTool {

    @Tool("搜索互联网上关于给定主题的虚构且有趣的故事")
    public String search(@P("topic") String topic) {
        try {
            String response = RestClient.create()
                    .get()
                    .uri("https://api.duckduckgo.com/html/?q=" + topic + " funny story")
                    .retrieve()
                    .body(String.class);
            // 简化：直接返回搜索结果的前500个字符
            return response != null ? response.substring(0, Math.min(500, response.length())) : "未找到故事";
        } catch (Exception e) {
            return "搜索出错: " + e.getMessage();
        }
    }
}
