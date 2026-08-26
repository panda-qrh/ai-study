package com.config;

import com.agents.sequence.AudienceEditor;
import com.agents.sequence.CreativeWriter;
import com.agents.sequence.NovelCreator;
import com.agents.sequence.StyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    /**
     * {@link CreativeWriter}
     *
     * @param chatModel
     * @return
     */
    @Bean
    public CreativeWriter creativeWriter(ChatModel chatModel) {
        return AgenticServices.agentBuilder(CreativeWriter.class)
                .chatModel(chatModel)
                .outputKey("story")
                .build();
    }

    /**
     * {@link AudienceEditor}
     *
     * @param chatModel
     * @return
     */
    @Bean
    public AudienceEditor audienceEditor(ChatModel chatModel) {
        return AgenticServices.agentBuilder(AudienceEditor.class)
                .chatModel(chatModel)
                .outputKey("story")
                .build();
    }

    /**
     * {@link AudienceEditor}
     *
     * @param chatModel
     * @return
     */
    @Bean
    public StyleEditor styleEditor(ChatModel chatModel) {
        return AgenticServices.agentBuilder(StyleEditor.class)
                .chatModel(chatModel)
                .outputKey("story")
                .build();
    }

    @Bean
    public NovelCreator novelCreator(StyleEditor styleEditor,AudienceEditor audienceEditor,CreativeWriter creativeWriter) {
        return AgenticServices.sequenceBuilder(NovelCreator.class)
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .outputKey("story")
                .build();
    }


    @Bean
    public UntypedAgent untypedNovelCreator(StyleEditor styleEditor,AudienceEditor audienceEditor,CreativeWriter creativeWriter) {
        return AgenticServices.sequenceBuilder()
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .outputKey("story")
                .build();

    }

}