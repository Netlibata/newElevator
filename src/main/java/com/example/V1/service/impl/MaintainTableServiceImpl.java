package com.example.V1.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.V1.Dto.MaintainTableDTO;
import com.example.V1.Dto.MaintainWithDataDTO;
import com.example.V1.commont.Result;
import com.example.V1.entity.MaintainTable;
import com.example.V1.entity.Users;
import com.example.V1.mapper.MaintainTableMapper;
import com.example.V1.mapper.UsersMapper;
import com.example.V1.service.IMaintainTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UsersMapper userMapper;

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

            log.info("前端传入id = {}, status = {}, sum = {},userId = {}", maintainTableDTO.getId(), maintainTableDTO.getStatus(), maintainTableDTO.getSum(), maintainTableDTO.getUserId());

            // 构造更新条件，确保只更新满足条件的记录
            LambdaUpdateWrapper<MaintainTable> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(MaintainTable::getId, maintainTableDTO.getId())
                    .eq(MaintainTable::getStatus, "待处理")
                    .eq(MaintainTable::getSum, 0);

            // 构建要更新的对象
            MaintainTable updateRecord = new MaintainTable();
            updateRecord.setStatus(maintainTableDTO.getStatus());
            updateRecord.setRemark(maintainTableDTO.getRemark());
            updateRecord.setUserId(maintainTableDTO.getUserId());
            updateRecord.setDescr(maintainTableDTO.getDescr());

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

            // 如果状态是已维护，更新用户表中的 condition 字段为“空闲”
            if ("已维护".equals(maintainTableDTO.getStatus())) {
                // 获取并打印用户信息
                Users user = userMapper.selectById(maintainTableDTO.getUserId());
                log.info("查询到用户: {}", user);

                // 更新用户状态
                if (user != null) {
                    user.setCondition("空闲");
                    int rowsUpdated = userMapper.updateById(user);
                    log.info("更新用户状态行数: {}", rowsUpdated);  // 记录更新的行数
                    if (rowsUpdated > 0) {
                        log.info("用户状态更新成功，用户id = {}, condition = 空闲", maintainTableDTO.getUserId());
                    } else {
                        log.error("用户状态更新失败，未能更新用户表，用户id = {}", maintainTableDTO.getUserId());
                    }
                }

            }

            log.info("后端存入后 id = {}, status = {}, sum = {}, update = {},userId = {}", maintainTableDTO.getId(), updateRecord.getStatus(), updateRecord.getSum(), update, maintainTableDTO.getUserId());

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

    /**
     * 增加新的维护记录
     * @param maintainTable
     * @return
     */
    @Override
    public Result<String> addMaintain(MaintainTable maintainTable) {
        try {
            if (maintainTable.getUserId() == null || maintainTable.getMtDataId() == null) {
                return Result.error("缺少用户ID或异常数据ID");
            }
            //前端需要传回数据
            boolean ab=this.save(maintainTable);
            if(!ab){
                return Result.error("维修记录上报失败");
            }

            return Result.success("维修记录上报成功");
        } catch (Exception e) {
            log.error("上报失败", e);
            return Result.error("系统出现异常上报失败：" + e.getMessage());
        }
    }
}
