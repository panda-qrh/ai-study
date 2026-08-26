package com.tools;

import dev.langchain4j.agent.tool.SearchBehavior;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class MyTools {

    @Tool(name="sumNumbers",value="Sums 2 given numbers")
    double sum(double a, double b) {
        return a + b;
    }

    @Tool(name="squareTheRoot",value="Returns a square root of a given number")
    double squareRoot(double x) {
        return Math.sqrt(x);
    }

}