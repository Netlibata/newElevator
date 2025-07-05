-- ----------------------------
-- Table structure for data_e_table
-- ----------------------------
CREATE TABLE IF NOT EXISTS data_e_table (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  create_time TEXT NOT NULL DEFAULT (datetime('now')),
  system_name TEXT NOT NULL,
  system_sq_name TEXT NOT NULL,
  e_name TEXT NOT NULL,
  e_data REAL NOT NULL
);

-- ----------------------------
-- Records of data_e_table
-- ----------------------------

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
  sum INTEGER NOT NULL DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
  FOREIGN KEY (mt_data_id) REFERENCES data_e_table(id) ON DELETE CASCADE
);

-- ----------------------------
-- Records of maintain_table
-- ----------------------------

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
-- Table structure for ai_table
-- ----------------------------
CREATE TABLE IF NOT EXISTS ai_table (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  e_id INTEGER NOT NULL,
  ai_code INTEGER,
  ai_result TEXT,
  ai_severity TEXT,
  FOREIGN KEY (e_id) REFERENCES data_e_table(id) ON DELETE CASCADE
);

-- ----------------------------
-- 开启外键支持（如需要外键再加约束）
-- ----------------------------
PRAGMA foreign_keys = ON;
