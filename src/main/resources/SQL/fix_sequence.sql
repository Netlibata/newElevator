-- 修复H2数据库序列问题的脚本

-- 创建public schema（如果不存在）
CREATE SCHEMA IF NOT EXISTS public;

-- 设置默认schema为public
SET SCHEMA public;

-- 重新创建系统序列
CREATE SEQUENCE IF NOT EXISTS "public"."SYSTEM_SEQUENCE_BFEA9648_5A8C_4039_BE8F_CE012920A7FC" START WITH 23 BELONGS_TO_TABLE;

-- 为各表创建序列
CREATE SEQUENCE IF NOT EXISTS "public"."data_e_table_seq" START WITH 22;
CREATE SEQUENCE IF NOT EXISTS "public"."ai_table_seq" START WITH 22;
CREATE SEQUENCE IF NOT EXISTS "public"."maintain_table_seq" START WITH 17;