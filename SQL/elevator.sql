-- ----------------------------
-- Table structure for data_e_table
-- ----------------------------
CREATE TABLE IF NOT EXISTS data_e_table (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  create_time TEXT NOT NULL DEFAULT (datetime('now')),
  system_name TEXT NOT NULL,
  system_sq_name TEXT NOT NULL,
  e_name TEXT NOT NULL,
  e_data REAL NOT NULL,
  ai_result TEXT NULL,
  ai_code INTEGER NULL,
  ai_severity TEXT NULL
);

-- ----------------------------
-- Records of data_e_table
-- ----------------------------
INSERT INTO data_e_table
SELECT 17, '2025-06-28 11:33:39', '曳引系统', '设备名字', '测试一下维护记录', 97.58, NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM data_e_table WHERE id = 17);
INSERT INTO data_e_table
SELECT 18, '2025-06-28 12:20:24', '门系统', '设备名字', '测试一下维护记录', 98.88, NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM data_e_table WHERE id = 18);
INSERT INTO data_e_table
SELECT 19, '2025-06-28 14:11:46', '门系统', '设备名字', '测试一下维护记录', 98.88, NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM data_e_table WHERE id = 19);
INSERT INTO data_e_table
SELECT 20, '2025-06-28 14:12:47', '曳引系统', '设备名字', '测试时间次数', 98.88, NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM data_e_table WHERE id = 20);
INSERT INTO data_e_table
SELECT 21, '2025-06-28 15:43:21', '曳引系统', '设备名字', '测试数据库', 98.88, NULL, NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM data_e_table WHERE id = 21);

-- ----------------------------
-- Table structure for maintain_table
-- ----------------------------
CREATE TABLE IF NOT EXISTS maintain_table (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  mt_time TEXT NOT NULL DEFAULT (datetime('now')),
  user_id INTEGER NULL,
  mt_data_id INTEGER NOT NULL,
  status TEXT NOT NULL DEFAULT '未维护',
  remark TEXT NULL,
  sum INTEGER NOT NULL DEFAULT 0
);

-- ----------------------------
-- Records of maintain_table
-- ----------------------------
INSERT INTO maintain_table
SELECT 5, '2025-06-28 14:28:55', 1002, 19, '已维护', NULL, 1
WHERE NOT EXISTS (SELECT 1 FROM maintain_table WHERE id = 5);
INSERT INTO maintain_table
SELECT 6, '2025-06-28 14:00:00', 1002, 20, '已维护', NULL, 1
WHERE NOT EXISTS (SELECT 1 FROM maintain_table WHERE id = 6);
INSERT INTO maintain_table
SELECT 7, '2025-06-28 15:43:21', NULL, 21, '未维护', NULL, 0
WHERE NOT EXISTS (SELECT 1 FROM maintain_table WHERE id = 7);

-- ----------------------------
-- Table structure for users
-- ----------------------------
CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY,
  user_name TEXT NOT NULL,
  user_phone TEXT NULL,
  position TEXT NULL
);

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO users
SELECT 1002, '王大锤', '155555558791', '待分配'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1002);
INSERT INTO users
SELECT 1006, '胡佳佳', '1967658813791', '待分配'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1006);
INSERT INTO users
SELECT 10011, '李芯湉', '1875357813791', '待分配'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 10011);
INSERT INTO users
SELECT 10089, '唐琪婉', '1875658813791', '待分配'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 10089);

-- ----------------------------
-- 开启外键支持（如需要外键再加约束）
-- ----------------------------
PRAGMA foreign_keys = ON;
