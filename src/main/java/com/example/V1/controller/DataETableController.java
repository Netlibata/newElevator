package com.example.V1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.service.IDataETableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 异常数据表 前端控制器
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Slf4j
@RestController
@RequestMapping("/data-etable")
public class DataETableController {

    @Autowired
    private IDataETableService dataETableService;

    /**
     * 异常数据接收接口，AI分析并存储
     */
    @PostMapping("/gain-data")
    public Result<String> getgainData(@RequestBody DataETable dataETable){
        return dataETableService.getgainData(dataETable);
    }

    /**
     * 分页查询异常数据（根据系统名称,id和子系统名称筛选）
     */
    @GetMapping("/selectData")
    public Result<IPage<DataETable>> getErrorData(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(value ="id",  required = false) Long id,
            @RequestParam(value ="systemName",  required = false) String systemName,
            @RequestParam(value ="systemSqName",  required = false) String systemSqName){
        log.info("current = {},size = {},id = {},systemName = {}，systemSqName = {}", current, size, id, systemName, systemSqName);
        return dataETableService.getErrorData(current,size,id,systemName,systemSqName);
    }
}
