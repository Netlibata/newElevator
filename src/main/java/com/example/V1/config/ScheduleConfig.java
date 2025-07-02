package com.example.V1.config;

import com.example.V1.mapper.DataETableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//定期清理数据库异常数据
@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private DataETableMapper dataETableMapper;

    /**
     * 每天凌晨5点检查是否超过100条，超过就删除最旧20条
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void deleteIfTooMuch() {
        int total = dataETableMapper.getTotalCount();
        log.info("当前数据总量为：{} 条", total);
        if (total > 100) {
            int count = dataETableMapper.deleteOldest20();
            log.info("超过100条，已删除最旧的 {} 条记录", count);
        } else {
            log.info("数据量未超限，无需清理");
        }
    }
}
