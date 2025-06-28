package com.example.V1.Dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

}
