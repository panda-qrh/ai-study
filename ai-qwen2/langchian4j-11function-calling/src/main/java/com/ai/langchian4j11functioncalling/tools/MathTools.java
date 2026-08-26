package com.ai.langchian4j11functioncalling.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

import java.math.BigDecimal;

public class MathTools {
    @Tool("计算两个数之和")
    public String sum(@P("加数") BigDecimal a, @P("被加数")BigDecimal b){
        System.out.println(a);
        System.out.println(b);
        return a.add(b).toString();
    }
}
