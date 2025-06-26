package com.example.V1.service.impl;

import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.mapper.DataETableMapper;
import com.example.V1.service.IDataETableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 异常数据表 服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Slf4j
@Service
public class DataETableServiceImpl extends ServiceImpl<DataETableMapper, DataETable> implements IDataETableService {

    /**
     * 异常数据接收
     */
    @Override
    public Result<String> getgainData(DataETable dataETable) {
        //添加数据到数据库中
        log.info("接收到数据：{}", dataETable);
        boolean save = this.save(dataETable);
        if(save){
            return Result.success("数据添加成功");
        }
        else{
            return Result.error("数据添加失败");
        }
    }
}
