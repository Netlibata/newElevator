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
 * 参数异常记录表，关联系统异常
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("parameter_anomalies")
public class ParameterAnomalies implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数异常主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联system_anomalies表的主键ID
     */
    @TableField("system_anomalies_id")
    private Integer systemAnomaliesId;

    /**
     * 异常参数名
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 异常参数值
     */
    @TableField("param_data")
    private String paramData;


}
