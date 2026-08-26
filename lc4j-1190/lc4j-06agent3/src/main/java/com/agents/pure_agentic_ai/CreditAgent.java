package com.agents.pure_agentic_ai;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CreditAgent {

    @SystemMessage("""
            您是一名银行柜员，只处理美元（USD）账户存款。
            账户余额以 USD 计价，只能存入 USD。
            """)
    @UserMessage("""
            将 ｛｛amount｝｝ USD 记入 ｛｛user｝｝ 的账户，并返回新的余额。
            """)
    @Agent("一个只处理美元（USD）账户存款的银行柜员")
    String credit(@V("user") String user, @V("amount") Double amount);
}
