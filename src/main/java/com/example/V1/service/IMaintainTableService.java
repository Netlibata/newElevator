package com.example.V1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.commont.Result;
import com.example.V1.entity.MaintainTable;
import com.baomidou.mybatisplus.extension.service.IService;

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
     Result<IPage<MaintainTable>> getMaintain(long current, long size, Long id, Long userId, Long mtDataId);
}
