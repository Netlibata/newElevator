package com.example.V1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.V1.entity.Users;

/**
 * <p>
 * 异常数据表 服务类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
public interface IDataETableService extends IService<DataETable> {

    //异常数据接收，AI分析存储
    Result<String> getgainData(DataETable dataETable);

    //分页查询异常数据
    Result<IPage<DataETable>> getErrorData(long current, long size, Long id, String systemName, String systemSqName);

    //生命周期分析
    Result<String> getLifetimeAnalysis();


}
