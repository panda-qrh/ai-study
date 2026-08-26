package com.ai.aiqwen.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InterviewQuestionTool {

    @Tool(name = "interviewQuestionSearch", value = """
            Retriever relevant interview questions from mianshiya.com based on a keyword.
            Use this tool when the user asks for interview question about specific technologies,
            programming concepts,or job-related topics.The input should be a clear term.
            """)
    public String searchInterviewQuestion(@P(value="the keyword to search") String keyword) {
        List<String> questions = new ArrayList<>();
        String encode = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String url = "https://www.mianshiya.com/search/all?searchText=" + encode;
        Document doc;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements questionElements = doc.select(".ant-table-cell > a");
        questionElements.forEach(el -> questions.add(el.text().trim()));
        return String.join("\n", questions);

    }
}
