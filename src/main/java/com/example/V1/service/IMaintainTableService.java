package com.example.V1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.Dto.MaintainTableDTO;
import com.example.V1.Dto.MaintainWithDataDTO;
import com.example.V1.commont.Result;
import com.example.V1.entity.MaintainTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 维护记录表 服务类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
public interface IMaintainTableService extends IService<MaintainTable> {

    //分页查询维护记录
    Result<IPage<MaintainWithDataDTO>> getMaintain(long current, long size, Long id, Long userId, String systemName, LocalDateTime mtTime);

     //更新维护记录
    Result<String> updateMaintain(MaintainTableDTO maintainTableDTO);
}
