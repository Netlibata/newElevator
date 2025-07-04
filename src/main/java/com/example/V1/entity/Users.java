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
 * 人员信息表
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 人员名字
     */
    @TableField("user_name")
    private String userName;

    /**
     * 人员电话
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 人员岗位
     */
    @TableField("position")
    private String position;


}
