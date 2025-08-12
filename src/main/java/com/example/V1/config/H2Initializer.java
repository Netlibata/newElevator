package com.example.V1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

//H2数据库初始化器，自动导入数据
@Slf4j
@Component
public class H2Initializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化H2数据库结构和数据...");
        try {
            // 检查数据库是否已经有数据
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'DATA_E_TABLE'", Integer.class);

            if (count != null && count > 0) {
                log.info("H2数据库已存在表结构，跳过初始化");
                return;
            }

            // 查找H2初始化脚本
            String sqlPath = "SQL/h2_data_import.sql";
            File sqlFile = new File(sqlPath);

            if (sqlFile.exists()) {
                String sqlScript = new String(Files.readAllBytes(sqlFile.toPath()), StandardCharsets.UTF_8);
                executeSqlScript(sqlScript);
                log.info("H2数据库结构和数据初始化成功！");
            } else {
                log.warn("H2初始化脚本文件不存在: {}", sqlFile.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("初始化H2数据库失败", e);
        }
    }

    private void executeSqlScript(String sqlScript) {
        // 按分号分割SQL语句，但要注意处理字符串中的分号
        String[] sqlStatements = sqlScript.split(";\\s*\\n");
        for (String sql : sqlStatements) {
            String trimmedSql = sql.trim();
            if (!trimmedSql.isEmpty() && !trimmedSql.startsWith("--")) {
                try {
                    // 如果SQL语句不以分号结尾，添加分号
                    if (!trimmedSql.endsWith(";")) {
                        trimmedSql += ";";
                    }
                    jdbcTemplate.execute(trimmedSql);
                    log.debug("执行SQL成功: {}", trimmedSql.substring(0, Math.min(50, trimmedSql.length())) + "...");
                } catch (Exception e) {
                    log.error("执行SQL失败: {}", trimmedSql.substring(0, Math.min(100, trimmedSql.length())), e);
                }
            }
        }
    }
}