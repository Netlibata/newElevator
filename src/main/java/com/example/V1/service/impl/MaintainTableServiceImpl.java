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

            // 先查询数据库中的旧记录
            MaintainTable oldRecord = this.getById(maintainTableDTO.getId());
            if (oldRecord == null) {
                return Result.error("记录不存在");
            }

            log.info("前端传入id = {}, status = {}, sum = {}", maintainTableDTO.getId(), maintainTableDTO.getStatus(), maintainTableDTO.getSum());

            // 构造更新条件，确保只更新满足条件的记录
            LambdaUpdateWrapper<MaintainTable> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(MaintainTable::getId, maintainTableDTO.getId())
                    .eq(MaintainTable::getStatus, "未维护")
                    .eq(MaintainTable::getSum, 0);

            // 构建要更新的对象
            MaintainTable updateRecord = new MaintainTable();
            updateRecord.setStatus(maintainTableDTO.getStatus());
            updateRecord.setRemark(maintainTableDTO.getRemark());
            updateRecord.setUserId(maintainTableDTO.getUserId());

            // 如果旧sum是0，则设置新sum为1并更新时间
            if (oldRecord.getSum() == 0) {
                updateRecord.setSum(1);
                updateRecord.setMtTime(LocalDateTime.now());
            } else {
                // 否则保持旧sum，避免无意覆盖
                updateRecord.setSum(oldRecord.getSum());
            }

            // 执行更新
            boolean update = this.update(updateRecord, updateWrapper);

            log.info("后端存入后 id = {}, status = {}, sum = {}, update = {}", maintainTableDTO.getId(), updateRecord.getStatus(), updateRecord.getSum(), update);

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
