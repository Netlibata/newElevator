package com.example.V1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("userName")
    private String userName;

    /**
     * 人员电话
     */
    @TableField("user_phone")
    @JsonProperty("userPhone")
    private String userPhone;

    /**
     * 人员岗位
     */
    @TableField("position")
    private String position;

    private String email;

    private String password;

    private String role;

    private String salt;

    /**
     * 人员状态
     */
    private String condition;
}
