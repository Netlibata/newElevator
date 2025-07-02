package com.example.V1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//识别数据库文件实现自动填充
@Slf4j
@Component
public class SQLiteInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化SQLite数据库结构...");
        try {
            File sqlFile = new File("SQL/elevator.sql");
            if (sqlFile.exists()) {
                String sqlScript = readFileToString(sqlFile);
                executeSqlScript(sqlScript);
                log.info("SQLite数据库结构初始化成功！");
            } else {
                log.warn("SQL脚本文件不存在: {}", sqlFile.getAbsolutePath());
            }
        } catch (Exception e) {
            log.error("初始化SQLite数据库结构失败", e);
        }
    }

    private String readFileToString(File file) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

    private void executeSqlScript(String sqlScript) {
        String[] sqlStatements = sqlScript.split(";");
        for (String sql : sqlStatements) {
            String trimmedSql = sql.trim();
            if (!trimmedSql.isEmpty()) {
                try {
                    jdbcTemplate.execute(trimmedSql);
                    log.debug("执行SQL: {}", trimmedSql);
                } catch (Exception e) {
                    log.error("执行SQL失败: {}", trimmedSql, e);
                }
            }
        }
    }
}