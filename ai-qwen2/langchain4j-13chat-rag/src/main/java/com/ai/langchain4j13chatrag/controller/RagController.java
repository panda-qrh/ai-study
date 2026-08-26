package com.ai.langchain4j13chatrag.controller;

import com.ai.langchain4j13chatrag.service.ChatAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RagController {
    @Resource
    private ChatAssistant chatAssistant;
    @Resource
    private EmbeddingStore<TextSegment> inMemoryEmbeddingStore;


    @GetMapping("/chat/rag/add")
    public String testAdd() {
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("E:\\A1_Projects\\Java_Projects\\ai\\ai-qwen2\\langchain4j-13chat-rag\\src\\main\\resources\\docs");
        EmbeddingStoreIngestor.ingest(documents, inMemoryEmbeddingStore);

        String result = chatAssistant.chat("有哪些常见的面试题");

        System.out.println(result);

        return result;
    }
}
