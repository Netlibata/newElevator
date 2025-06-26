package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 维护记录表
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("maintain_table")
public class MaintainTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 维护时间，自动填入
     */
    @TableField(value = "mt_time")
    private LocalDateTime mtTime;

    /**
     * 维护人员ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 被维护的异常数据ID
     */
    @TableField(value = "mt_data_id")
    private Integer mtDataId;

    /**
     * 维护状态（0:未维护, 1:已维护）
     */
    @TableField(value = "status")
    private Boolean status;


}
