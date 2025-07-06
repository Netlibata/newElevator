package com.example.V1.config;

import com.example.V1.entity.PromptKnowledge;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class AiPredictsLifespanConfig {
    public String aiPredictsLifespan(List<PromptKnowledge> knowledgeList, String data) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名电梯故障分析专家，以下是你总结的一些经验知识：\n");
        for (int i = 0; i < Math.min(10, knowledgeList.size()); i++) {
            PromptKnowledge k = knowledgeList.get(i);
            sb.append("Q: ").append(k.getPrompt()).append("\n");
            sb.append("A: ").append(k.getCompletion()).append("\n");
        }

        sb.append("\n当前查出来的异常数据为：\n");
        sb.append(new ObjectMapper().writeValueAsString(data)).append("\n");

        sb.append("请根据上面知识和异常数据，详细判断电梯的寿命，返回格式如下：\n");
        sb.append("{\n");
        sb.append("  \"main\": \"请根据查出来的异常细信息简要分析与判断电梯的寿命\"\n");
        sb.append("  \"message\": \"请根据查出来的异常信息详细的分析与判断当前电梯的寿命\"\n");
        sb.append("}\n");

        sb.append("\n你必须严格按照以上JSON格式返回，不要添加任何其他内容，并且还有其他注意事项：\n");
        sb.append("1. 不要在JSON前后添加任何额外文字或解释\n");
        sb.append("2. 确保JSON格式正确，可以被解析\n");
        sb.append("3. 你的回复必须是一个有效的JSON对象，不要包含任何markdown标记或其他格式\n");
        sb.append("4. 返回的main字段一定要是重点，分析要专业\n");
        sb.append("5. message必须要详细，分析要专业\"\n");
        sb.append("6. message字段应该包含详细的寿命分析，需要展现出电梯故障分析专家的专业\n");
        sb.append("7. 要返回具体的剩余寿命\n");

        return sb.toString();
    }

}
