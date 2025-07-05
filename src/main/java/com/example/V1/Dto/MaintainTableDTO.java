package com.example.V1.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
/**
 * 创建 maintain_table 的更新数据传输对象
 */
@Data
public class MaintainTableDTO {
    private String status;
    private String remark;

    @JsonProperty("userId")
    private Integer userId;

    private Integer id;
    private int sum;
}
