package com.example.V1.config;

import com.example.V1.entity.DataETable;
import com.example.V1.entity.PromptKnowledge;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class buildPromptWithKnowleConfig {
    public String buildPromptWithKnowledge(List<PromptKnowledge> knowledgeList, DataETable data) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名电梯故障分析专家，以下是你总结的经验知识：\n");
        for (int i = 0; i < Math.min(10, knowledgeList.size()); i++) {
            PromptKnowledge k = knowledgeList.get(i);
            sb.append("Q: ").append(k.getPrompt()).append("\n");
            sb.append("A: ").append(k.getCompletion()).append("\n");
        }
        sb.append("\n当前上传的电梯异常数据为：\n");
        sb.append(new ObjectMapper().writeValueAsString(data)).append("\n");
        sb.append("请根据上面知识和异常数据，判断故障类型、原因、维修建议，并判断是否弹窗报警（1=弹窗，0=不弹窗）。\n");
        sb.append("请以JSON格式返回，格式如下：\n");
        sb.append("{\"message\": \"通过异常数据得知[故障类型]，分析[原因]，建议[维修建议]，[1/0]\"}");
        return sb.toString();
    }
}
