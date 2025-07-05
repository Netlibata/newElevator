package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * AI分析存储表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ai_table")
public class AiTable implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 关联异常数据表id
     */
    @TableField(value = "e_id")
    @JsonProperty("eId")
    private Integer eId;

    /**
     * AI分析结果
     */
    @TableField("ai_result")
    @JsonProperty("aiResult")
    private String aiResult;

    /**
     * AI分析结果代码（0=警告，1=严重故障）
     */
    @TableField("ai_code")
    @JsonProperty("aiCode")
    private Integer aiCode;

    /**
     * AI分析严重程度描述
     */
    @TableField("ai_severity")
    @JsonProperty("aiSeverity")
    private String aiSeverity;
}
