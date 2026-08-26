package com.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.web.client.RestClient;

public class ArxivCrawler {

    @Tool("搜索与给定主题相关的科学论文，返回论文标题和摘要")
    public String search(@P("topic") String topic) {
        try {
            String response = RestClient.create()
                    .get()
                    .uri("https://export.arxiv.org/find/cs/1/ti:+AND+" + topic + "/0/1/0/all/0/1")
                    .retrieve()
                    .body(String.class);
            return response != null ? response.substring(0, Math.min(1000, response.length())) : "未找到相关论文";
        } catch (Exception e) {
            return "搜索出错: " + e.getMessage();
        }
    }
}
