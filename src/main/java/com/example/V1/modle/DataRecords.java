package com.example.V1.modle;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("data_records")
public class DataRecords implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 故障分析
     */
    @TableField("fault_analysis")
    private String faultAnalysis;

    /**
     * 解决建议
     */
    @TableField("solutions")
    private String solutions;

    /**
     * 生成时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 处理状态（默认'未处理'，'已处理'）
     */
    @TableField("status")
    private String status;

    /**
     * 更新时间
     */
    @TableField("new_data")
    private LocalDateTime newData;

    /**
     * 故障类型
     * 曳引机、导向系统、轿厢系统、电梯门、安全保护装置、电气控制系统、电力拖动系统、重量平衡系统
     */
    @TableField("id")
    private String type;

    /**
     * 电梯编号
     */
    @TableField("elevator_id")
    private String elevatorId;


}
