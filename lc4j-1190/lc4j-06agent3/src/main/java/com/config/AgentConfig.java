package com.config;

import com.agents.pure_agentic_ai.CreditAgent;
import com.agents.pure_agentic_ai.ExchangeAgent;
import com.agents.pure_agentic_ai.SupervisorAgent;
import com.agents.pure_agentic_ai.WithdrawAgent;
import com.tools.BankTool;
import com.tools.ExchangeTool;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AgentConfig {
    public BankTool bt = BankTool.createAccount(Map.of("张三", 10000.0, "李四", 10000.0));


    @Bean
    public SupervisorAgent bankSupervisor(ChatModel chatModel) {
        WithdrawAgent withdrawAgent = AgenticServices.agentBuilder(WithdrawAgent.class).chatModel(chatModel).tools(bt).build();
        CreditAgent creditAgent = AgenticServices.agentBuilder(CreditAgent.class).chatModel(chatModel).tools(bt).build();
        ExchangeAgent exchangeAgent = AgenticServices.agentBuilder(ExchangeAgent.class).chatModel(chatModel).tools(new ExchangeTool()).build();

        return AgenticServices.supervisorBuilder(SupervisorAgent.class)
                .chatModel(chatModel)
                .subAgents(withdrawAgent, creditAgent, exchangeAgent)
                .responseStrategy(SupervisorResponseStrategy.SUMMARY)
                .build();
    }


}
