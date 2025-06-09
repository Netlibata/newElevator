package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.commont.Result;
import com.example.V1.modle.DataRecords;
import com.example.V1.mapper.DataRecordsMapper;
import com.example.V1.service.IDataRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-09
 */
@Service
@Slf4j
public class DataRecordsServiceImpl extends ServiceImpl<DataRecordsMapper, DataRecords> implements IDataRecordsService {

    @Autowired
    DataRecordsMapper dataRecordsMapper;

    /**
     * 查询电梯故障记录(用于主系统统一查看所有电梯的故障记录)
     * @return Result<IPage<DataRecords>>
     */
    @Override
    public Result<IPage<DataRecords>> getErrorData(Long current, Long size){
        // 查询全部记录
        try{
            IPage<DataRecords> page = new Page<>(current, size);
            page = dataRecordsMapper.selectPage(page, new QueryWrapper<>());

            //isEmpty(),java中自带的方法用来判断集合是否为空
            if(page == null || page.getRecords().isEmpty()){
                log.info("未查询到相关数据");
                return Result.success("未查询到相关数据",new Page<>());
            }
            else{
                log.info("分页查询试卷题目成功:,当前页={}, 每页大小={}, 总记录数={}, 总页数={}",
                         current, size, page.getTotal(), page.getPages());
                return Result.success("查询成功",page);
            }
        }catch (Exception e){
                log.error("查询电梯故障记录失败",e);
                return Result.error("查询电梯故障记录失败");
        }
    }
}
