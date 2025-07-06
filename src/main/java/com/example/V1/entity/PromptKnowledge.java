package com.example.V1.entity;

import lombok.Data;

//AI 知识库实体
@Data
public class PromptKnowledge {
    private String prompt;
    private String completion;
}
