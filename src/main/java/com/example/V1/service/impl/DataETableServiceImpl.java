package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.entity.MaintainTable;
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

        /**MaintainTable maintainTable = new MaintainTable();
        //添加到维护记录表中
        maintainTable.setMtTime(dataETable.getCreateTime());
         */

        if(save){
            return Result.success("数据添加成功");
        }
        else{
            return Result.error("数据添加失败");
        }
    }

    /**
     * 分页查询异常信息
     */
    @Override
    public Result<IPage<DataETable>> getErrorData(long current,
                                                  long size,
                                                  Long id,
                                                  String systemName,
                                                  String systemSqName) {

       try{
           //创建分页对象
           Page<DataETable> page = new Page<>(current, size);

           // 构建查询条件
           LambdaQueryWrapper<DataETable> queryWrapper = new LambdaQueryWrapper<>();

           // 如果指定了题库ID，则按题库ID查询
           if (id != null) {
               queryWrapper.eq(DataETable::getId, id);
           }
           if(systemName != null && !systemName.trim().isEmpty()){
               queryWrapper.like(DataETable::getSystemName, systemName);
           }
           if(systemSqName != null){
               queryWrapper.like(DataETable::getSystemSqName, systemSqName);
           }

           // 按更新时间降序排序
           queryWrapper.orderByDesc(DataETable::getCreateTime);

           IPage<DataETable> pageData = this.page(page, queryWrapper);

           // 如果没有查询到数据，返回空的分页对象
           if (pageData == null || pageData.getRecords().isEmpty()) {
               log.info("未查询到相关题目数据: 当前页={}, 每页大小={}", current, size);
               return Result.success("未查询到相关数据", pageData);
           }

           log.info("分页查询题目成功: 当前页={}, 每页大小={}, 总记录数={}, 总页数={}",
                   current, size, pageData.getTotal(), pageData.getPages());
           return Result.success("查询成功", pageData);
       }catch (Exception e){
           log.error("系统异常，查询失败",e);
           return Result.error("系统异常，查询失败");
       }
    }
}
