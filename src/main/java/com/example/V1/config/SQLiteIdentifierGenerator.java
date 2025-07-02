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
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT COALESCE(MAX(id), 0) FROM " + tableName)) {
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Long currentMaxId = rs.getLong(1);
                    return currentMaxId + 1;
                }
                return 1L;
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取下一个ID失败: " + e.getMessage(), e);
        }
    }
}