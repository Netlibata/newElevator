package com.example.V1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.V1.entity.AiTable;
import com.example.V1.mapper.AiTableMapper;
import com.example.V1.service.IAiTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * AI表服务实现类
 */
@Slf4j
@Service
public class AiTableServiceImpl extends ServiceImpl<AiTableMapper, AiTable> implements IAiTableService {

}
