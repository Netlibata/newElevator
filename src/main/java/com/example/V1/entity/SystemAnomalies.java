package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统类异常表，关联异常主表
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_anomalies")
public class SystemAnomalies implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统异常表主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联data_records表的主键ID
     */
    @TableField("record_id")
    private Integer recordId;

    /**
     * 系统ID
     */
    @TableField("system_id")
    private String systemId;

    /**
     * 系统名称
     */
    @TableField("system_name")
    private String systemName;

    /**
     * 故障状态
     */
    @TableField("status")
    private String status;

    /**
     * 异常系统数据记录
     */
    @TableField("parameters")
    private String parameters;


}
