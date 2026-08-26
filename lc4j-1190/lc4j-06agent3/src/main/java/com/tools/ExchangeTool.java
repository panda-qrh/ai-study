package com.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExchangeTool {

    private static final Map<String, Double> RATES = new ConcurrentHashMap<>();
    static {
        RATES.put("EUR_USD", 1.09);
        RATES.put("USD_EUR", 0.9174);
        RATES.put("EUR_GBP", 0.86);
        RATES.put("GBP_EUR", 1.1628);
        RATES.put("USD_GBP", 0.79);
        RATES.put("GBP_USD", 1.2658);
        RATES.put("EUR_JPY", 168.5);
        RATES.put("USD_JPY", 154.59);
        RATES.put("EUR_CNY", 7.92);
        RATES.put("USD_CNY", 7.27);
    }

    @Tool("将给定金额的货币从原始货币兑换为目标货币")
    public Double exchange(@P("originalCurrency") String originalCurrency, @P("amount") Double amount, @P("targetCurrency") String targetCurrency) {
        if (amount == null || amount <= 0) {
            return 0.0;
        }
        if (originalCurrency == null || targetCurrency == null) {
            return 0.0;
        }
        String from = originalCurrency.trim().toUpperCase();
        String to = targetCurrency.trim().toUpperCase();
        if (from.equals(to)) {
            return Math.round(amount * 100.0) / 100.0;
        }
        Double rate = RATES.get(from + "_" + to);
        if (rate == null) {
            return 0.0;
        }
        return Math.round(amount * rate * 100.0) / 100.0;
    }
}
