package com.example.V1.controller;


import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.service.IDataETableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 异常数据表 前端控制器
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@RestController
@RequestMapping("/data-etable")
public class DataETableController {

    @Autowired
    private IDataETableService dataETableService;

    /**
     * 异常数据接收接口
     */
    @PostMapping("/gain-data")
    public Result<String> getgainData(@RequestBody DataETable dataETable){
        return dataETableService.getgainData(dataETable);
    }
}
