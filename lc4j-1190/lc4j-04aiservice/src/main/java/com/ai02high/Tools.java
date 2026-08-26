package com.ai02high;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class Tools {
    @Tool(name="sum",value="两数之和,用于加法计算，将两个数相加后返回最终结果,如：3+2=5，3+3=6，3加2=5，三加三等于6")
    public Double sum(@P("被加数") Double a, @P("加数") Double b) {
        System.out.printf("两数相加：%s + %s = %s\n", a, b, a + b);
        return a + b;
    }

    @Tool(name="multi",value = "两数相乘，用于乘法计算，将两个数相乘后返回最终结果，如：3 × 2 =6，3*2=6")
    public Double multi(@P("被乘数") Double a, @P("乘数") Double b) {
        System.out.printf("两数相乘：%s × %s = %s\n", a, b, a * b);
        return a * b;
    }
}