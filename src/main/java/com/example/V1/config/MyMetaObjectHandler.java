package com.example.V1.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus自动填充处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始执行插入自动填充...");
        // 自动填充字段
        this.strictInsertFill(metaObject, "mtTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        Object statusVal = getFieldValByName("status", metaObject);
        if (statusVal == null) {
            this.strictInsertFill(metaObject, "status", String.class, "未维护");
        }
    }

   @Override
    public void updateFill(MetaObject metaObject) {
        // 更新操作暂不需要自动填充
    }
}