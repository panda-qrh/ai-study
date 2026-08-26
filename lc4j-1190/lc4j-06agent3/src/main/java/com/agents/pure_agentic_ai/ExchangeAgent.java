package com.agents.pure_agentic_ai;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ExchangeAgent {

    @UserMessage("""
            您负责货币兑换，账户体系以 USD 为基准货币。
            使用工具将 ｛｛amount｝｝ ｛｛originalCurrency｝｝ 兑换为 USD，
            或把 USD 兑换为 ｛｛targetCurrency｝｝。
            只返回工具计算出的最终金额，不添加任何其他文字。
            """)
    @Agent("一个将货币兑换为 USD 或从 USD 兑换为其他货币的兑换商")
    Double exchange(@V("originalCurrency") String originalCurrency, @V("amount") Double amount, @V("targetCurrency") String targetCurrency);
}
