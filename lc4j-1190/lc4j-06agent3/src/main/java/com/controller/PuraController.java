package com.controller;

import com.agents.pure_agentic_ai.SupervisorAgent;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PuraController {
    @Resource
    private SupervisorAgent bankSupervisor;

    @GetMapping("/pura/transf")
    public String transfer(@RequestParam(defaultValue = "从张三的账户向李四的账户转账100欧元") String message) {
        Map<String, Object> input = Map.of(
                "request", "从张三的账户向李四的账户转账100欧元",
                "supervisorContext", """
                        账户体系（每个账户一种货币）：
                        - 张三：1000 USD
                        - 李四：1000 USD
                        可用代理：
                        - exchange：将任意货币兑换为 USD，或 USD 兑换为其他货币。
                          汇率表：EUR→USD=1.09, USD→EUR=0.9174, USD→GBP=0.79, GBP→USD=1.2658,
                                EUR→GBP=0.86, GBP→EUR=1.1628, EUR→JPY=168.5, USD→JPY=154.59,
                                EUR→CNY=7.92, USD→CNY=7.27
                        - withdraw：从用户账户提取 USD（账户本身是 USD，直接用 USD 金额）。
                        - credit：向用户账户存入 USD。
                        - getBalance / getAccountInfo：查询用户余额和货币类型。
                        - done：任务完成后返回完整结果。

                        执行规则：
                        1. 转账涉及非 USD 货币时，第一步必须调用 exchange 将金额兑换为 USD。
                           例：100欧元 → exchange(EUR, 100, USD) → 109.0 USD。
                        2. 用兑换后的 USD 金额调用 withdraw 从付款人扣款。
                        3. 调用 credit 向收款人存入同等 USD。
                        4. done 返回完整结果，包含汇率、兑换后金额、双方新余额。
                        """
        );
        String res = bankSupervisor.invoke("从张三的账户向李四的账户转账100欧元","""
                        账户体系（每个账户一种货币）：
                        - 张三：1000 USD
                        - 李四：1000 USD
                        可用代理：
                        - exchange：将任意货币兑换为 USD，或 USD 兑换为其他货币。
                          汇率表：EUR→USD=1.09, USD→EUR=0.9174, USD→GBP=0.79, GBP→USD=1.2658,
                                EUR→GBP=0.86, GBP→EUR=1.1628, EUR→JPY=168.5, USD→JPY=154.59,
                                EUR→CNY=7.92, USD→CNY=7.27
                        - withdraw：从用户账户提取 USD（账户本身是 USD，直接用 USD 金额）。
                        - credit：向用户账户存入 USD。
                        - getBalance / getAccountInfo：查询用户余额和货币类型。
                        - done：任务完成后返回完整结果。

                        执行规则：
                        1. 转账涉及非 USD 货币时，第一步必须调用 exchange 将金额兑换为 USD。
                           例：100欧元 → exchange(EUR, 100, USD) → 109.0 USD。
                        2. 用兑换后的 USD 金额调用 withdraw 从付款人扣款。
                        3. 调用 credit 向收款人存入同等 USD。
                        4. done 返回完整结果，包含汇率、兑换后金额、双方新余额。
                        """);
        return res;
    }
}
