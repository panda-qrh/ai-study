package com.ai.aiqwen.config;

import com.ai.aiqwen.tool.InterviewQuestionTool;
import com.ai.aiqwen.service.AiCoderHelperService;
import com.ai.aiqwen.tool.MathTools;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class QwenConfig {
    @Resource
    private ChatModel qwenChatModel;
    @Resource
    private EmbeddingModel qwenEmbeddingModel;
    @Resource
    private EmbeddingStore embeddingStore;
    @Resource
    private StreamingChatModel qwenStreamingChatModel;

    /**
     * 使用AI高级服务<br/>
     * <p>引入langchain4j-spring-boot-starter就不需要自己创建bean了，只需要正在类上打上@AiService</p>
     * 给AiCoderHelperService类创建AI服务
     *
     * @return AiCoderHelperService Bean
     */
    @Bean
    public AiCoderHelperService aiCoderHelperService() {
        //创建会话记忆
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        return AiServices.builder(AiCoderHelperService.class)
                .chatModel(qwenChatModel)
//                .streamingChatModel(qwenStreamingChatModel)
//                .chatMemory(chatMemory) //会话记忆
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
//                .contentRetriever(contentRetriever()) //rag检索
                .tools(new InterviewQuestionTool(),new MathTools())
                .executeToolsConcurrently()
                .build();
    }

//    @Bean
    public ContentRetriever contentRetriever() {
        List<Document> document = FileSystemDocumentLoader.loadDocuments("src/main/resources/docs");
        DocumentByParagraphSplitter splitter = new DocumentByParagraphSplitter(1000, 200);

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .textSegmentTransformer(textSegment ->
                        TextSegment.from(textSegment.metadata().getString("file_name") + "\n" + textSegment.text(),
                                textSegment.metadata())
                )
                .embeddingModel(qwenEmbeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(qwenEmbeddingModel)
                .maxResults(5)
                .minScore(0.75)
                .build();
    }

}
