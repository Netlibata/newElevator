package com.example.V1.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 创建查询维护记录表的插入对象
 */
@Getter
@Setter
public class MaintainWithDataDTO {
    // 维护ID
    private Integer id;

    // 状态
    private String status;

    // 备注
    private String remark;

    // 维护人员ID
    private Long userId;

    //时间
    private LocalDateTime mtTime;


    // 系统名称（来自异常表）
    private String systemName;

    private String descr;

}
