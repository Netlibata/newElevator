package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.entity.MaintainTable;
import com.example.V1.mapper.MaintainTableMapper;
import com.example.V1.service.IMaintainTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 维护记录表 服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-26
 */
@Slf4j
@Service
public class MaintainTableServiceImpl extends ServiceImpl<MaintainTableMapper, MaintainTable> implements IMaintainTableService {

    /**
     * 分页查询维护记录
     */
    @Override
    public Result<IPage<MaintainTable>> getMaintain(long current,
                              long size,
                              Long id,
                              Long userId,
                              Long mtDataId) {
        try{
            //创建分页对象
            Page<MaintainTable> page = new Page<>(current, size);

            // 构建查询条件
            LambdaQueryWrapper<MaintainTable> queryWrapper = new LambdaQueryWrapper<>();

            // 如果指定了题库ID，则按题库ID查询
            if (id != null ) {
                queryWrapper.eq(MaintainTable::getId, id);
            }
            if(userId != null ){
                queryWrapper.like(MaintainTable::getUserId, userId);
            }
            if(mtDataId != null){
                queryWrapper.like(MaintainTable::getMtDataId, mtDataId);
            }

            // 按更新时间降序排序
            queryWrapper.orderByDesc(MaintainTable::getMtTime);

            IPage<MaintainTable> pageData = this.page(page, queryWrapper);

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
