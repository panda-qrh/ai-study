package com.ai.langchain4j09prompt.pojo;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Data;

@Data
@StructuredPrompt("根据中国{{legal}}法律，回答以下问题：{{question}}")
public class QuestionPrompt {
    private String legal;
    private String question;
}
