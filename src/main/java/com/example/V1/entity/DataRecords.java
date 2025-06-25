package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异常主记录表
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("data_records")
public class DataRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，自动增长
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 异常类型，默认就是system
     */
    private String type;

    /**
     * 异常生成时间，自动生成
     */
    private LocalDateTime createDate;


}
