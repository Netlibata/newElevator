package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异常数据表
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("data_e_table")
public class DataETable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 异常生成的时间，自动填入
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 系统名称
     */
    @TableField("system_name")
    @JsonProperty("systemName")
    private String systemName;

    /**
     * 子设备名称
     */
    @TableField("system_sq_name")
    @JsonProperty("systemSqName")
    private String systemSqName;

    /**
     * 异常数据的名称
     */
    @JsonProperty("eName")
    @TableField("e_name")
    private String eName;

    /**
     * 异常数据值
     */
    @JsonProperty("eData")
    @TableField("e_data")
    private String eData;

    /**
     * AI分析结果
     */
    @TableField("ai_result")
    private String aiResult;

    /**
     * AI分析结果代码（0=警告，1=严重故障）
     */
    @TableField("ai_code")
    private Integer aiCode;

    /**
     * AI分析严重程度描述
     */
    @TableField("ai_severity")
    private String aiSeverity;

}
