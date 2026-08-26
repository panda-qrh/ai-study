package com.config;

import com.agents.sequence.AudienceEditor;
import com.agents.sequence.CreativeWriter;
import com.agents.sequence.NovelCreator;
import com.agents.sequence.StyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.agent.ErrorRecoveryResult;
import dev.langchain4j.agentic.agent.MissingArgumentException;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public CreativeWriter creativeWriter(ChatModel chatModel, ChatModel chatModelFlash) {
        return AgenticServices.agentBuilder(CreativeWriter.class)
                .chatModel(scope -> {//多模型选择
                    String state = (String) scope.readState("critique");
                    if (state == null) {
                        System.out.println("creativeWriter bean--chatModel============");
                        return chatModel;
                    } else {
                        System.out.println("creativeWriter bean--chatModelFlash============");
                        return chatModelFlash;
                    }
                })
                .listener(new AgentListener(){//监控
                    @Override
                    public void beforeAgentInvocation(AgentRequest agentRequest) {
                        System.out.println("调用CreativeWriter之前，topic = "+agentRequest.inputs().get("topic"));
                    }
                })
                .outputKey("story")
                .build();
    }

    @Bean
    public AudienceEditor audienceEditor(ChatModel chatModel) {
        return AgenticServices.agentBuilder(AudienceEditor.class)
                .chatModel(chatModel)
                .outputKey("story")
                .build();
    }

    @Bean
    public StyleEditor styleEditor(ChatModel chatModel) {
        return AgenticServices.agentBuilder(StyleEditor.class)
                .chatModel(chatModel)
                .outputKey("story")
                .build();
    }

    @Bean
    public NovelCreator novelCreator(StyleEditor styleEditor, AudienceEditor audienceEditor, CreativeWriter creativeWriter) {
        return AgenticServices.sequenceBuilder(NovelCreator.class)
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .outputKey("story")
                .build();
    }


    @Bean
    public UntypedAgent untypedNovelCreator(StyleEditor styleEditor, AudienceEditor audienceEditor, CreativeWriter creativeWriter) {
        return AgenticServices.sequenceBuilder()
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .errorHandler((errorContext) -> { //异常处理
                    if (errorContext.agentName().equals("generateStory") &&
                            errorContext.exception() instanceof MissingArgumentException mae &&
                            mae.argumentName().equalsIgnoreCase("topic")) {
                        errorContext.agenticScope().writeState("topic", "龙与巫师");
                        return ErrorRecoveryResult.retry();
                    }
                    return ErrorRecoveryResult.throwException();
                })
                .compensateOnError(true)
                .outputKey("story")
                .build();

    }

}