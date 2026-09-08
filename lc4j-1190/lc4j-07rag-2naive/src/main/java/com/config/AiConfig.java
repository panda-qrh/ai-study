package com.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModelFactory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {
    private static final String apiKey = System.getenv("API_KEY");
    private static final String url = "https://api.stepfun.com/step_plan/v1";
    private static final String model = "step-3.7-flash";

    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }

    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(url)
                .modelName(model)
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(5)
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .build();
    }

    /**
     * Naive RAG — ContentRetriever 完整配置
     * <p>
     * Naive RAG 的核心流程（手动组装每个环节）：
     * <pre>
     * {@code
     * Document Loader  →  Document Parser  →  Document Splitter
     * ↓                    ↓                    ↓
     * List<Document>     解析文件内容        切割为 TextSegment
     * ↓                    ↓                    ↓
     * Document Transformer（可选）        Text Segment Transformer（可选）
     * ↓                    ↓                    ↓
     * Embedding Model（向量化）
     * ↓
     * Embedding Store（存储向量）
     * ↑
     * EmbeddingStore Ingestor（串联以上所有步骤）
     * ↓
     * ContentRetriever（检索：用户问题 → 向量库 → 最相关片段）
     * }
     * </pre>
     */
    @Bean
    public ContentRetriever contentRetriever() {

        // ==================== 1. Document Parser ====================
        DocumentParser documentParser=new ApacheTikaDocumentParser(true);
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("docs",documentParser);
        for (Document doc : documents) {
            System.out.println("  - " + doc.metadata().getString(Document.FILE_NAME));
        }
        // ==================== 3. Document Splitter ====================
        // 将大文档切割为多个 TextSegment，便于精准检索
        // chunkSize=500: 每个片段最多 500 个字符
        // overlap=50:    相邻片段重叠 50 个字符，保证语义连贯
        DocumentSplitter splitter = new DocumentByParagraphSplitter(500, 50);
        // 执行分割，查看 TextSegment 产出
//        List<TextSegment> textSegments = splitter.splitAll(documents);

        // ==================== 4. Document Transformer（可选）====================
        // 可以在此对 Document 做自定义转换 需要自己定制的 DocumentTransformer 方案
        // 通过 EmbeddingStoreIngestor.builder().documentTransformer(...) 注入
        // 例如：过滤空白文档、统一编码、添加自定义 Metadata 等
        // 本例中不做额外转换，直接进入下一步

        // ==================== 5. Embedding Model ====================
        // 将文本片段向量化的模型，采用默认自带的embeddingModel bge-small-en-v1.5
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModelFactory().create();

        // ==================== 6. Embedding Store ====================
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // ==================== 7. EmbeddingStore Ingestor ====================
        //  串联整个入库流水线： Document → Parser(解析) → Splitter(分割) → EmbeddingModel(向量化) → Store(存储)
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        // 执行文档入库（分割 + 向量化 + 存储一步完成）
        IngestionResult ingestionResult = ingestor.ingest(documents);
        System.out.println("文档入库，token消耗： " + ingestionResult.tokenUsage());

        // ==================== 8. ContentRetriever ====================
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(10)
                .minScore(0.5)
                .build();
    }
}