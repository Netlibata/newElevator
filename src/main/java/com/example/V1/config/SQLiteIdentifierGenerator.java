package com.example.V1.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//自动填充id和日期
@Component
public class SQLiteIdentifierGenerator implements IdentifierGenerator {

    @Resource
    private DataSource dataSource;

    @Override
    public Number nextId(Object entity) {
        String tableName = getTableName(entity);
        return getNextId(tableName);
    }

    private String getTableName(Object entity) {
        // 首先尝试从@TableName注解获取表名
        TableName tableNameAnnotation = entity.getClass().getAnnotation(TableName.class);
        if (tableNameAnnotation != null) {
            return tableNameAnnotation.value();
        }

        // 如果没有@TableName注解，则使用类名转换
        String className = entity.getClass().getSimpleName();
        return camelToUnderscore(className);
    }

    private String camelToUnderscore(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private Long getNextId(String tableName) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT MAX(id) FROM " + tableName);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                long maxId = rs.getLong(1); // 获取 MAX(id)
                return rs.wasNull() ? 1L : maxId + 1; // 处理 NULL 情况
            }
            return 1L; // 表为空时的默认值
        } catch (SQLException e) {
            throw new RuntimeException("获取下一个ID失败: " + e.getMessage(), e);
        }
    }
}
