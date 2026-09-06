package com.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class Calculator {

    @Tool(name="add",value="加法计算。给定两个数求这两个数的和，")
    long add(@P(description = "被加数") long a, @P(description = "加数") long b) {
        return a + b;
    }
}