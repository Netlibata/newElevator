package com.example.V1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.V1.commont.Result;
import com.example.V1.modle.DataRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-09
 */
public interface IDataRecordsService extends IService<DataRecords> {

    /**
     * 查询电梯故障记录
     * 用于在主系统中查看所有电梯故障记录
     */
    Result<IPage<DataRecords>> getErrorData(Long current, Long size);

}
