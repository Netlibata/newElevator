package com.example.V1.config;

import com.example.V1.entity.PromptKnowledge;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
//AI工具类
public class KnowledgeLoader {
    public static List<PromptKnowledge> loadKnowledgeFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = KnowledgeLoader.class.getResourceAsStream("/knowledge.jsonl");
        return Arrays.asList(mapper.readValue(is, PromptKnowledge[].class));
    }
}
