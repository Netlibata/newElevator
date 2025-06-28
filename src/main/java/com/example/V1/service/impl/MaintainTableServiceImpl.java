package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.Dto.MaintainTableDTO;
import com.example.V1.Dto.MaintainWithDataDTO;
import com.example.V1.commont.Result;
import com.example.V1.entity.DataETable;
import com.example.V1.entity.MaintainTable;
import com.example.V1.mapper.MaintainTableMapper;
import com.example.V1.service.IMaintainTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Autowired
    private MaintainTableMapper maintainMapper;

    /**
     * 分页查询维护记录
     */
    @Override
    public Result<IPage<MaintainWithDataDTO>> getMaintain(long current,
                                                          long size,
                                                          Long id,
                                                          Long userId,
                                                          String systemName,
                                                          LocalDateTime mtTime) {
        try {
            // 创建分页对象
            Page<MaintainWithDataDTO> page = new Page<>(current, size);

            // 调用联表分页查询 Mapper 方法
            IPage<MaintainWithDataDTO> pageData = maintainMapper.getMaintainWithJoin(page, id, userId, systemName,mtTime);

            // 判空处理
            if (pageData == null || pageData.getRecords().isEmpty()) {
                log.info("未查询到相关维护数据: 当前页={}, 每页大小={}", current, size);
                return Result.success("未查询到相关数据", pageData);
            }


            // 正常返回
            log.info("分页查询维护记录成功: 当前页={}, 每页大小={}, 总记录数={}, 总页数={}",
                    current, size, pageData.getTotal(), pageData.getPages());
            return Result.success("查询成功", pageData);
        } catch (Exception e) {
            log.error("系统异常，查询失败", e);
            return Result.error("系统异常，查询失败");
        }
    }


    /**
     * 更新维护记录表
     */
    @Override
    public Result<String> updateMaintain(MaintainTableDTO maintainTableDTO) {
        try {
            if (maintainTableDTO.getId() == null) {
                return Result.error("ID不能为空");
            }

            log.info("前端传入id = {},status = {},sum = {}", maintainTableDTO.getId(), maintainTableDTO.getStatus(), maintainTableDTO.getSum());

            LambdaUpdateWrapper<MaintainTable> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(MaintainTable::getId, maintainTableDTO.getId())
                    .eq(MaintainTable::getStatus, "未维护")
                    .eq(MaintainTable::getSum, 0)
                    .set(MaintainTable::getSum,1);


            MaintainTable maintainTable = new MaintainTable();
            maintainTable.setStatus(maintainTableDTO.getStatus());
            maintainTable.setRemark(maintainTableDTO.getRemark());
            maintainTable.setUserId(maintainTableDTO.getUserId());

            boolean update = this.update(maintainTable, updateWrapper);

            // 先获取旧的sum
            int oldSum = this.getById(maintainTableDTO.getId()).getSum();
            // 设置新sum值为1
            maintainTable.setSum(1);
            // 如果旧sum是0，就设置时间
            if (oldSum == 0) {
                maintainTable.setMtTime(LocalDateTime.now());
            }


            log.info("后端存入后id = {},status = {},sum = {},update =  {}", maintainTable.getId(), maintainTable.getStatus(), maintainTable.getSum(), update);

            if (update) {
                log.info("数据更新成功");
                return Result.success("数据更新成功");
            } else {
                log.info("数据更新失败，条件未匹配或无数据");
                return Result.error("数据更新失败，条件未匹配或无数据");
            }
        } catch (Exception e) {
            log.error("系统异常，更新失败", e);
            return Result.error("系统异常，更新失败");
        }
    }

}
