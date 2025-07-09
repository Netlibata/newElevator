package com.example.V1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.Dto.MaintainTableDTO;
import com.example.V1.Dto.MaintainWithDataDTO;
import com.example.V1.commont.Result;
import com.example.V1.entity.MaintainTable;
import com.example.V1.service.IMaintainTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public Result<IPage<MaintainWithDataDTO>> getMaintain(@RequestParam(defaultValue = "1") long current,
                                                          @RequestParam(defaultValue = "9") long size,
                                                          @RequestParam(value ="id",  required = false) Long id,
                                                          @RequestParam(value ="userId",  required = false) Long userId,
                                                          @RequestParam(value ="systemName",  required = false) String systemName,
                                                          @RequestParam(value ="mtTime",  required = false) LocalDateTime mtTime) {
        log.info("current = {},size = {},id = {},userId = {},mtDataId = {}", current, size, id, userId, systemName);
        return imaintainTableService.getMaintain(current, size, id, userId, systemName,mtTime);
    }

    /**
     * 更新维护记录
     */
    @PostMapping("/update-maintain")
    public Result<String> updateMaintain(@RequestBody MaintainTableDTO maintainTableDTO) {
        return imaintainTableService.updateMaintain(maintainTableDTO);
    }

    /**
     * 添加维护记录
     */
    @PostMapping("/add-maintain")
    public Result<String> addMaintain(@RequestBody MaintainTable maintainTable) {
        return imaintainTableService.addMaintain(maintainTable);
    }
}
