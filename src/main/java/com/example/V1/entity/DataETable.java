package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 异常生成的时间，自动填入
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 系统名称
     */
    @TableField("system_name")
    private String systemName;

    /**
     * 子设备名称
     */
    @TableField("system_sq_name")
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
    private double eData;


}
