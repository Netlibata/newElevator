package com.example.V1.service.impl;

import com.example.V1.entity.SystemAnomalies;
import com.example.V1.mapper.SystemAnomaliesMapper;
import com.example.V1.service.ISystemAnomaliesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统类异常表，关联异常主表 服务实现类
 * </p>
 *
 * @author Netlibata
 * @since 2025-06-25
 */
@Service
public class SystemAnomaliesServiceImpl extends ServiceImpl<SystemAnomaliesMapper, SystemAnomalies> implements ISystemAnomaliesService {

}
