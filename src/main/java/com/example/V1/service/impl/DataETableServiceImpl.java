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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;

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
    private OpenAiChatModel openAiChatModel;

    /**
     * 异常数据接收
     */
    public Result<String> getgainData(DataETable dataETable) {
        try {
            //打印Id
            log.info("接收到的数据Id为：{}", dataETable.getId());
            //添加数据到数据库中
            log.info("接收到数据：{}", dataETable);
            boolean save = this.save(dataETable);

            MaintainTable maintainTable = new MaintainTable();
            //添加到维护记录表中异常数据id
            maintainTable.setMtDataId(dataETable.getId());
            maintainTableServicce.save(maintainTable);

            // 如果 save 失败，返回错误信息
            if (!save) {
                return Result.error("数据添加失败");
            }
            return Result.success("数据添加成功");

        } catch (Exception e) {
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
            Object aiResponseObj = openAiChatModel.call(prompt);
            String aiResponse = aiResponseObj.toString();
            log.debug("AI原始响应：{}", aiResponse);

            // 清理多余格式
            aiResponse = aiResponse.replaceAll("```json", "").replaceAll("```", "").trim();

            if (aiResponse == null || aiResponse.isEmpty() || !aiResponse.startsWith("{") || !aiResponse.endsWith("}")) {
                return Result.error("AI 返回的不是有效的 JSON 格式: " + aiResponse);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(aiResponse);
            String message = responseJson.get("message").asText().replace("\\n", "\n");

            ObjectNode updatedResponseJson = objectMapper.createObjectNode();
            updatedResponseJson.put("message", message);
            return Result.success(updatedResponseJson.toString());

        } catch (Exception e) {
            log.error("系统异常，分析失败", e);
            return Result.error("系统异常，分析失败");
        }
    }
}
