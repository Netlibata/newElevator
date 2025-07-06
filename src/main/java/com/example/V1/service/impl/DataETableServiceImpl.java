package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.Dto.DataETableForAiDTO;
import com.example.V1.commont.Result;
import com.example.V1.config.KnowledgeLoader;
import com.example.V1.config.buildPromptWithKnowleConfig;
import com.example.V1.entity.AiTable;
import com.example.V1.entity.DataETable;
import com.example.V1.entity.MaintainTable;
import com.example.V1.entity.PromptKnowledge;
import com.example.V1.mapper.DataETableMapper;
import com.example.V1.service.IDataETableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * <p>
 * 异常数据表 服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Slf4j
@Service
public class DataETableServiceImpl extends ServiceImpl<DataETableMapper, DataETable> implements IDataETableService {



    @Autowired
    private MaintainTableServiceImpl maintainTableService;

    @Autowired
    private AiTableServiceImpl aiTableService;

    @Autowired
    private OpenAiChatModel knowledgeLoader;


    /**
     * 异常数据接收，AI分析并存储（AI失败也存）
     */
    public Result<String> getgainData(DataETable dataETable) {
        try {
            // 1. 设置默认字段
            if (dataETable.getSystemName() == null || dataETable.getSystemName().isEmpty()) {
                dataETable.setSystemName("未知系统");
            }
            if (dataETable.getSystemSqName() == null || dataETable.getSystemSqName().isEmpty()) {
                dataETable.setSystemSqName("未知组件");
            }

            // 2. 保存异常数据
            boolean saved = this.save(dataETable);
            if (!saved || dataETable.getId() == null) {
                return Result.error("异常数据保存失败");
            }

            //在维护表中插入异常表的id
            maintainTableService.save(new MaintainTable().setMtDataId(dataETable.getId()));

            Integer errorId = dataETable.getId();

            // 3. 构建 AI 输入数据
            DataETableForAiDTO dataForAI = new DataETableForAiDTO();
            dataForAI.setSystemName(dataETable.getSystemName());
            dataForAI.setSystemSqName(dataETable.getSystemSqName());
            dataForAI.setEName(dataETable.getEName());
            dataForAI.setESqName(dataETable.getEData());

            // 4. 构建提示词
            List<PromptKnowledge> knowledgeList = KnowledgeLoader.loadKnowledgeFromJson();
            String prompt = new buildPromptWithKnowleConfig().buildPromptWithKnowledge(knowledgeList, dataForAI);

            // 5. 调用 AI 并处理响应
            String message = "AI响应异常";
            int code = 0;
            String severity = "未知";
            String suggestion = "AI未返回建议";

            try {
                Object aiResponseObj = knowledgeLoader.call(prompt);
                String aiResponse = aiResponseObj.toString();

                int jsonStart = aiResponse.indexOf("{");
                int jsonEnd = aiResponse.lastIndexOf("}");
                if (jsonStart >= 0 && jsonEnd > jsonStart) {
                    aiResponse = aiResponse.substring(jsonStart, jsonEnd + 1);
                } else {
                    aiResponse = aiResponse.replaceAll("```json", "").replaceAll("```", "").trim();
                }

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(aiResponse);
                message = jsonNode.has("aiResult") ? jsonNode.get("aiResult").asText().replace("\\n", "\n") : "AI未返回分析内容";
                code = jsonNode.has("aiCode") ? jsonNode.get("aiCode").asInt() : 0;
                suggestion = jsonNode.has("建议") ? jsonNode.get("建议").asText().replace("\\n", "\n") : "AI未返回建议";
                severity = code == 1 ? "严重故障" : "警告";

            } catch (Exception aiEx) {
                log.warn("AI解析失败，使用默认值: {}", aiEx.getMessage());
            }

            // 6. 保存AI结果
            AiTable aiTable = new AiTable();
            aiTable.setEId(errorId);
            aiTable.setAiResult(message);
            aiTable.setAiCode(code);
            aiTable.setAiSeverity(severity);
            aiTableService.save(aiTable);

            // 7. 构造返回
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode resultJson = mapper.createObjectNode();
            resultJson.put("id", "d-" + System.currentTimeMillis());
            resultJson.put("systemName", dataETable.getSystemName());
            resultJson.put("systemSqName", dataETable.getSystemSqName());
            resultJson.put("eName", dataETable.getEName());
            resultJson.put("eData", dataETable.getEData());
            resultJson.put("aiCode", code);
            resultJson.put("aiResult", message);
            resultJson.put("建议", suggestion);

            if ("AI响应异常".equals(message)) {
                return Result.error("异常数据已保存，但AI响应异常");
            } else {
                return Result.success("异常数据保存，AI分析成功", resultJson.toString());
            }

        } catch (Exception e) {
            log.error("保存并AI分析异常失败", e);
            return Result.error("保存或AI分析失败: " + e.getMessage());
        }
    }


    /**
     * 分页查询异常信息
     */
   @Override
    public Result<IPage<DataETable>> getErrorData(long current,
                                                  long size,
                                                  Long id,
                                                  String systemName,
                                                  String systemSqName) {

       try{
           //创建分页对象
           Page<DataETable> page = new Page<>(current, size);

           // 构建查询条件
           LambdaQueryWrapper<DataETable> queryWrapper = new LambdaQueryWrapper<>();

           // 如果指定了题库ID，则按题库ID查询
           if (id != null) {
               queryWrapper.eq(DataETable::getId, id);
           }
           if(systemName != null && !systemName.trim().isEmpty()){
               queryWrapper.like(DataETable::getSystemName, systemName);
           }
           if(systemSqName != null){
               queryWrapper.like(DataETable::getSystemSqName, systemSqName);
           }

           // 按更新时间降序排序
           queryWrapper.orderByDesc(DataETable::getCreateTime);

           IPage<DataETable> pageData = this.page(page, queryWrapper);

           // 如果没有查询到数据，返回空的分页对象
           if (pageData == null || pageData.getRecords().isEmpty()) {
               log.info("未查询到相关题目数据: 当前页={}, 每页大小={}", current, size);
               return Result.success("未查询到相关数据", pageData);
           }

           log.info("分页查询题目成功: 当前页={}, 每页大小={}, 总记录数={}, 总页数={}",
                   current, size, pageData.getTotal(), pageData.getPages());
           return Result.success("查询成功", pageData);
       }catch (Exception e){
           log.error("系统异常，查询失败",e);
           return Result.error("系统异常，查询失败");
       }
    }

}
