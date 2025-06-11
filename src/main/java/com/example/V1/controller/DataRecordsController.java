package com.example.V1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.commont.Result;
import com.example.V1.modle.DataRecords;
import com.example.V1.service.impl.DataRecordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-09
 */
@RestController
@RequestMapping("/data-records")
public class DataRecordsController {

    @Autowired
    DataRecordsServiceImpl dataRecordsService;

    /**
     * 分页查询电梯故障记录
     * 用在主系统中统一查看所有电梯的故障记录
     */
    @GetMapping("/selectData")
    public Result<IPage<DataRecords>> getErrorData(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size){
        return dataRecordsService.getErrorData(current,size);
    }

    /**
     * 根据故障类型查找电梯故障记录
     */
    @PostMapping("/selecType")
    public Result<List<DataRecords>> getErrorDataByType(@RequestBody DataRecords type){
        return dataRecordsService.getErrorDataByType(type);
    }

}
