package com.ai.aiqwen.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.Result;

import java.math.BigDecimal;

public class MathTools {
    @Tool(name="sum",value = "Calculate the sum of a and b")
    public Result<String> sum(@P(value="a",required = true) BigDecimal a, @P(value="b",required = true)BigDecimal b){
        String res = a.add(b).toString();
        return  Result.<String>builder().content(res).build();
    }

    @Tool(name="multiply",value = "Calculate the multiply of a and b")
    public String multiply(BigDecimal a,BigDecimal b){
        String res = a.multiply(b).toString();
        return res;
    }

    @Tool(name="divide",value = "Calculate the divide of a and b")
    public String divide(BigDecimal a,BigDecimal b){
        String res = a.divide(b).toString();
        return res;
    }

}
