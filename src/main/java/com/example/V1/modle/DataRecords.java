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
     * 生成时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 故障类型
     */
    @TableField("type")
    private String type;

    /**
     * 异常数值
     */
    @TableField("e_data")
    private String eData;

    /**
     * 异常数据的名字
     */
    @TableField("e_name")
    private String eName;


}
