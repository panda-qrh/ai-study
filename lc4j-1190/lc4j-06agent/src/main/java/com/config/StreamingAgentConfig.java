package com.config;

import com.agents.sequence.AudienceEditor;
import com.agents.sequence.CreativeWriter;
import com.agents.sequence.NovelCreator;
import com.agents.sequence.StyleEditor;
import com.agents.streaming.StreamingAudienceEditor;
import com.agents.streaming.StreamingCreativeWriter;
import com.agents.streaming.StreamingNovelCreator;
import com.agents.streaming.StreamingStyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamingAgentConfig {

    @Bean
    public StreamingCreativeWriter streamingCreativeWriter(StreamingChatModel streamingChatModel) {
        return AgenticServices.agentBuilder(StreamingCreativeWriter.class)
                .streamingChatModel(streamingChatModel)
                .outputKey("story")
                .build();
    }


    @Bean
    public StreamingAudienceEditor streamingAudienceEditor(StreamingChatModel streamingChatModel) {
        return AgenticServices.agentBuilder(StreamingAudienceEditor.class)
                .streamingChatModel(streamingChatModel)
                .outputKey("story")
                .build();
    }


    @Bean
    public StreamingStyleEditor streamingStyleEditor(StreamingChatModel streamingChatModel) {
        return AgenticServices.agentBuilder(StreamingStyleEditor.class)
                .streamingChatModel(streamingChatModel)
                .outputKey("story")
                .build();
    }

    @Bean
    public StreamingNovelCreator streamingNovelCreator(StreamingCreativeWriter streamingCreativeWriter, StreamingAudienceEditor streamingAudienceEditor, StreamingStyleEditor streamingStyleEditor) {
        return AgenticServices.sequenceBuilder(StreamingNovelCreator.class)
                .subAgents(streamingCreativeWriter, streamingAudienceEditor, streamingStyleEditor)
                .outputKey("story")
                .build();
    }


    @Bean
    public UntypedAgent untypedStreamingNovelCreator(StreamingCreativeWriter streamingCreativeWriter, StreamingAudienceEditor streamingAudienceEditor, StreamingStyleEditor streamingStyleEditor) {
        return AgenticServices.sequenceBuilder()
                .subAgents(streamingCreativeWriter, streamingAudienceEditor, streamingStyleEditor)
                .outputKey("story")
                .build();

    }

}