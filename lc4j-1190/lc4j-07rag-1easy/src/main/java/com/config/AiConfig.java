package com.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;


@Configuration
public class AiConfig {

    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://api.stepfun.com/step_plan/v1";
    private static final String model = "step-3.7-flash";

    @Bean
    public StreamingChatModel streamingChatModel(){
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }

    @Bean
    public ChatModel chatModel(){
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return memoryId-> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .maxMessages(5)
                .build();
    }

    @Bean
    public ContentRetriever contentRetriever(){
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("docs");
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

}