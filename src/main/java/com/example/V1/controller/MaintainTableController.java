package com.example.V1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.commont.Result;
import com.example.V1.entity.MaintainTable;
import com.example.V1.service.IMaintainTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 维护记录表 前端控制器
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Slf4j
@RestController
@RequestMapping("/maintain-table")
public class MaintainTableController {

    @Autowired
    private IMaintainTableService imaintainTableService;
    /**
     * 分页获取维护记录
     */
    @GetMapping("/get-maintain")
    public Result<IPage<MaintainTable>> getMaintain(@RequestParam(defaultValue = "1") long current,
                                                    @RequestParam(defaultValue = "10") long size,
                                                    @RequestParam(value ="id",  required = false) Long id,
                                                    @RequestParam(value ="userId",  required = false) Long userId,
                                                    @RequestParam(value ="mtDataId",  required = false) Long mtDataId) {
        log.info("current = {},size = {},id = {},userId = {},mtDataId = {}", current, size, id, userId, mtDataId);
        return imaintainTableService.getMaintain(current, size, id, userId, mtDataId);
    }
}
