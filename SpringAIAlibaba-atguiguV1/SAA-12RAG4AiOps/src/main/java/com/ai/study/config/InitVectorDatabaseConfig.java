package com.ai.study.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class InitVectorDatabaseConfig {
    @Autowired
    private VectorStore vectorStore;


    @PostConstruct
    public void init() {

        ClassPathResource classPathResource = new ClassPathResource("ops.txt");
        // 1.读取文件
        TextReader textReader = new TextReader(classPathResource);
        textReader.setCharset(Charset.defaultCharset());
        // 2.文件转换成向量（分词）
        List<Document> list = new TokenTextSplitter().transform(textReader.read());

        // 3.写入向量数据库（Redis）,无法去重复版
        vectorStore.add(list);
    }
}
