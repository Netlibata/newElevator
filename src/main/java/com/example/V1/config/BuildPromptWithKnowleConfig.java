package com.example.V1.config;

import com.example.V1.Dto.DataETableForAiDTO;
import com.example.V1.entity.PromptKnowledge;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class BuildPromptWithKnowleConfig {
    public String buildPromptWithKnowledge(List<PromptKnowledge> knowledgeList, DataETableForAiDTO data) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名电梯故障分析专家，以下是你总结的一些经验知识：\n");
        for (int i = 0; i < Math.min(10, knowledgeList.size()); i++) {
            PromptKnowledge k = knowledgeList.get(i);
            sb.append("Q: ").append(k.getPrompt()).append("\n");
            sb.append("A: ").append(k.getCompletion()).append("\n");
        }

        sb.append("\n当前上传的电梯异常数据为：\n");
        sb.append(new ObjectMapper().writeValueAsString(data)).append("\n");

        sb.append("\n你的任务是判断该异常的故障等级、分析结果和给出处理建议，并以如下格式输出：\n");
        sb.append("```json\n");
        sb.append("{\n");
        sb.append("  \"id\": \"d-系统生成，如可忽略则填null\",\n");
        sb.append("  \"systemName\": \"").append(data.getSystemName()).append("\",\n");
        sb.append("  \"systemSqName\": \"").append(data.getSystemSqName()).append("\",\n");
        sb.append("  \"eName\": \"").append(data.getEName()).append("\",\n");
        sb.append("  \"eData\": \"").append(data.getESqName()).append("\",\n");
        sb.append("  \"aiCode\": 0,\n");
        sb.append("  \"aiResult\": \"分析结果：请填写详细的故障分析，如：……\",\n");
        sb.append("  \"建议\": \"请填写详细的处理建议以及建议分配任务给维护人员还是技术人员，如：……\"\n");
        sb.append("}\n");
        sb.append("```\n");

        sb.append("\n你必须严格按照以上JSON格式返回，不要添加任何其他内容，并且还有其他注意事项：\n");
        sb.append("1. 必须使用JSON格式，包含message和code两个字段\n");
        sb.append("2. code只能是数字0或1，不能使用字符串\n");
        sb.append("3. 不要在JSON前后添加任何额外文字或解释\n");
        sb.append("4. 确保JSON格式正确，可以被解析\n");
        sb.append("5. 你的回复必须是一个有效的JSON对象，不要包含任何markdown标记或其他格式\n");
        sb.append("6. message字段应该包含详细的故障分析和维修建议，需要展现出电梯故障分析专家的专业，不要简单地返回'系统异常'\n");
        sb.append("7. 请根据上面知识和异常数据，详细判断故障类型、详细原因、详细维修建议，并判断故障等级：\n");
        sb.append("8. 严重故障（code=1）：需要立即停止电梯运行，显示红色弹窗，必须维修人员处理后才能恢复运行\n");
        sb.append("9. 警告（code=0）：电梯可以继续运行，显示黄色弹窗，可以忽略或上报\n");
        sb.append("10. 必须返回建议分配给技术人员或者维护人员\n");

        return sb.toString();
    }
}
