package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.commont.Result;
import com.example.V1.config.KnowledgeLoader;
import com.example.V1.config.buildPromptWithKnowleConfig;
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
    private MaintainTableServiceImpl maintainTableServicce;

    @Autowired
    private OpenAiChatModel knowledgeLoader;



    /**
     * 异常数据接收
     */
    public Result<DataETable> getgainData(DataETable dataETable) {
        try {
            //打印Id
            log.info("接收到的数据Id为：{}", dataETable.getId());
            //添加数据到数据库中
            log.info("接收到数据：{}", dataETable);
            
            // 验证必填字段
            if (dataETable.getSystemName() == null || dataETable.getSystemName().isEmpty()) {
                log.warn("系统名称为空，设置默认值");
                dataETable.setSystemName("曳引系统");
            }
            
            if (dataETable.getSystemSqName() == null || dataETable.getSystemSqName().isEmpty()) {
                log.warn("子系统名称为空，设置默认值");
                dataETable.setSystemSqName("未知组件");
            }
            
            boolean save = this.save(dataETable);

            // 如果 save 失败，返回错误信息
            if (!save) {
                return Result.error("数据添加失败");
            }
            
            // 获取保存后的ID
            Integer savedId = dataETable.getId();
            log.info("保存成功，生成的ID为：{}", savedId);
            
            if (savedId != null) {
                MaintainTable maintainTable = new MaintainTable();
                //添加到维护记录表中异常数据id
                maintainTable.setMtDataId(savedId);
                maintainTableServicce.save(maintainTable);
            } else {
                log.warn("未能获取保存的ID，无法创建维护记录");
            }
            
            // 返回保存的数据对象，包含ID
            return Result.success("数据添加成功", dataETable);

        } catch (Exception e) {
            log.error("存储异常数据失败", e);
            return Result.error("出现异常存储失败");
        }
    }

    /**
     * 从AI响应中提取JSON部分
     * AI有时会在JSON前后添加说明文字，需要提取出纯JSON
     * @param response AI的原始响应
     * @return 提取出的JSON字符串
     */
    private String extractJsonFromResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            return "[]";
        }

        // 尝试查找JSON数组开始和结束位置
        int startIndex = response.indexOf('[');
        int endIndex = response.lastIndexOf(']');

        if (startIndex >= 0 && endIndex > startIndex) {
            return response.substring(startIndex, endIndex + 1);
        }

        // 如果没有找到数组格式，尝试查找对象格式
        startIndex = response.indexOf('{');
        endIndex = response.lastIndexOf('}');

        if (startIndex >= 0 && endIndex > startIndex) {
            return response.substring(startIndex, endIndex + 1);
        }

        // 如果都没找到，返回原始响应
        return response;
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

    @Override
    public Result<String> sendDataToAI(DataETable dataETable) {
        try {
            // 加载知识库 JSON 文件
            List<PromptKnowledge> knowledgeList = KnowledgeLoader.loadKnowledgeFromJson();

            // 构建提示词
            String prompt = new buildPromptWithKnowleConfig().buildPromptWithKnowledge(knowledgeList, dataETable);

            // 调用 AI
            log.info("发送给AI的提示词: {}", prompt);
            Object aiResponseObj = knowledgeLoader.call(prompt);
            String aiResponse = aiResponseObj.toString();
            log.info("AI原始响应：{}", aiResponse);

            // 清理多余格式
            log.info("AI原始响应: {}", aiResponse);
            
            // 提取JSON部分
            int jsonStart = aiResponse.indexOf("{");
            int jsonEnd = aiResponse.lastIndexOf("}");
            
            if (jsonStart >= 0 && jsonEnd > jsonStart) {
                aiResponse = aiResponse.substring(jsonStart, jsonEnd + 1);
            } else {
                // 尝试清理markdown格式
                aiResponse = aiResponse.replaceAll("```json", "").replaceAll("```", "").trim();
            }
            
            log.info("清理后的AI响应: {}", aiResponse);
            
            if (aiResponse == null || aiResponse.isEmpty() || !aiResponse.startsWith("{") || !aiResponse.endsWith("}")) {
                return Result.error("AI 返回的不是有效的 JSON 格式: " + aiResponse);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(aiResponse);
            String message = responseJson.get("message").asText().replace("\\n", "\n");
            
            // 获取code，如果不存在则默认为0（警告）
            int code = 0;
            if (responseJson.has("code")) {
                code = responseJson.get("code").asInt();
            }
            
            // 创建包含message和code的响应
            ObjectNode updatedResponseJson = objectMapper.createObjectNode();
            updatedResponseJson.put("message", message);
            updatedResponseJson.put("code", code);
            
            // 根据code判断是严重故障还是警告
            String severity = code == 1 ? "严重故障" : "警告";
            log.info("AI分析结果: {} - {}", severity, message);
            
            // 将AI分析结果更新到异常数据表中
            if (dataETable.getId() != null) {
                // 查询数据库中的记录
                DataETable existingData = this.getById(dataETable.getId());
                if (existingData != null) {
                    // 更新AI分析结果
                    existingData.setAiResult(message);
                    existingData.setAiCode(code);
                    existingData.setAiSeverity(severity);
                    
                    // 保存更新后的记录
                    boolean updated = this.updateById(existingData);
                    if (!updated) {
                        log.warn("更新异常数据表中的AI分析结果失败");
                    } else {
                        log.info("已将AI分析结果更新到异常数据表，ID: {}", existingData.getId());
                    }
                } else {
                    log.warn("未找到ID为{}的异常数据记录", dataETable.getId());
                }
            } else {
                log.warn("异常数据没有ID，无法更新AI分析结果");
            }
            
            return Result.success(updatedResponseJson.toString());

        } catch (Exception e) {
            log.error("系统异常，分析失败", e);
            
            // 创建一个包含错误信息的响应
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode errorResponseJson = objectMapper.createObjectNode();
                errorResponseJson.put("message", "系统异常，分析失败: " + e.getMessage());
                errorResponseJson.put("code", 1); // 默认为严重故障
                
                // 将错误信息更新到异常数据表中
                if (dataETable != null && dataETable.getId() != null) {
                    DataETable existingData = this.getById(dataETable.getId());
                    if (existingData != null) {
                        existingData.setAiResult("系统异常，分析失败: " + e.getMessage());
                        existingData.setAiCode(1);
                        existingData.setAiSeverity("严重故障");
                        this.updateById(existingData);
                    }
                }
                
                return Result.success(errorResponseJson.toString());
            } catch (Exception ex) {
                log.error("创建错误响应失败", ex);
                return Result.error("系统异常，分析失败");
            }
        }
    }
}
