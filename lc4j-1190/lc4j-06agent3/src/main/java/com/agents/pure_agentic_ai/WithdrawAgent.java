package com.agents.pure_agentic_ai;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface WithdrawAgent {

    @SystemMessage("""
            您是一名银行柜员，只处理美元（USD）账户取款。
            账户余额以 USD 计价，只能提取 USD。
            """)
    @UserMessage("""
            从｛｛user｝｝的账户中提取 ｛｛amount｝｝ USD 并返回新余额。
            """)
    @Agent("一个只处理美元（USD）账户取款的银行柜员")
    String withdraw(@V("user") String user, @V("amount") Double amount);
}
