package com.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.util.HashMap;
import java.util.Map;

public class BankTool {

    public record Account(String currency, double balance) {}

    private static final Map<String, Account> accounts = new HashMap<>();

    /**
     * 创建账户，value 为初始余额（默认 USD）
     */
    public static BankTool createAccount(Map<String, Double> map) {
        map.forEach((user, balance) -> {
            accounts.put(user, new Account("USD", balance));
        });
        return new BankTool();
    }

    @Tool("查询指定用户的账户信息（货币类型和余额）")
    public String getAccountInfo(@P("user") String user) {
        Account account = accounts.get(user);
        if (account == null) {
            throw new RuntimeException("未找到用户(" + user + ")的账户");
        }
        return account.currency() + " " + account.balance();
    }

    @Tool("查询指定用户的账户余额")
    public double getBalance(@P("user") String user) {
        Account account = accounts.get(user);
        if (account == null) {
            throw new RuntimeException("未找到用户(" + user + ")的余额");
        }
        return account.balance();
    }

    @Tool("将给定金额记入给定用户账户并返回新余额")
    public Double credit(@P("user") String user, @P("amount") Double amount) {
        Account account = accounts.get(user);
        if (account == null) {
            throw new RuntimeException("未找到用户(" + user + ")的余额");
        }
        double newBalance = account.balance() + amount;
        accounts.put(user, new Account(account.currency(), newBalance));
        return newBalance;
    }

    @Tool("从给定用户账户提取给定金额并返回新余额")
    public Double withdraw(@P("user") String user, @P("amount") Double amount) {
        Account account = accounts.get(user);
        if (account == null) {
            throw new RuntimeException("未找到用户(" + user + ")的余额");
        }
        if (account.balance() < amount) {
            throw new RuntimeException("用户(" + user + ")余额不足，当前余额: " + account.balance() + " " + account.currency());
        }
        double newBalance = account.balance() - amount;
        accounts.put(user, new Account(account.currency(), newBalance));
        return newBalance;
    }
}
