PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "data_e_table" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "create_time" TEXT(255) NOT NULL,
  "system_name" TEXT(255) NOT NULL,
  "system_sq_name" TEXT(255) NOT NULL,
  "e_name" TEXT(255) NOT NULL,
  "e_data" TEXT(255) NOT NULL
);
INSERT INTO data_e_table VALUES(1,'2025-07-10T15:44:08.277094100','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(2,'2025-07-10T15:44:37.274184700','导向系统','导轨','导轨垂直度偏差','0.8');
INSERT INTO data_e_table VALUES(3,'2025-07-10T15:45:06.635505600','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(4,'2025-07-10T16:08:28.869961400','曳引系统','曳引机','电机温度','90°C');
INSERT INTO data_e_table VALUES(5,'2025-07-11T11:38:14.383239','导向系统','导轨','导轨垂直度偏差','0.8');
INSERT INTO data_e_table VALUES(6,'2025-07-11T11:47:02.566911100','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(7,'2025-07-11T11:47:26.857737400','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(8,'2025-07-11T12:17:04.349945800','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(9,'2025-07-11T12:18:09.907216800','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(10,'2025-07-11T12:18:57.034779700','门系统','门机','开关门时间','4.5');
INSERT INTO data_e_table VALUES(11,'2025-07-11T12:23:14.415401500','曳引系统','曳引机','电机温度','90°C');
INSERT INTO data_e_table VALUES(12,'2025-07-11T13:04:44.193630500','门系统','门机','开关门时间','4.5');
INSERT INTO data_e_table VALUES(13,'2025-07-11T13:10:47.091410300','曳引系统','曳引机','电机温度','90°C');
INSERT INTO data_e_table VALUES(14,'2025-07-11T13:27:08.481878700','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(15,'2025-07-11T13:41:37.905233100','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(16,'2025-07-11T13:42:24.040337200','门系统','门机','开关门时间','4.5');
INSERT INTO data_e_table VALUES(17,'2025-07-11T13:43:10.017762','电气控制系统','电源','电压波动','15%');
INSERT INTO data_e_table VALUES(18,'2025-07-11T13:43:55.792849100','曳引系统','曳引机','电机温度','90°C');
INSERT INTO data_e_table VALUES(19,'2025-07-12T13:18:48.868392600','门系统','门机','开关门时间','4.5');
INSERT INTO data_e_table VALUES(20,'2025-07-12T13:48:16.343967900','曳引系统','曳引机','电机温度','90°C');
INSERT INTO data_e_table VALUES(21,'2025-07-13T15:30:49.336859600','导向系统','导轨','导轨垂直度偏差','0.8');
CREATE TABLE IF NOT EXISTS "ai_table" (
  "id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "e_id" integer NOT NULL,
  "ai_code" integer,
  "ai_result" text,
  "ai_severity" text,
  CONSTRAINT "e_id" FOREIGN KEY ("e_id") REFERENCES "data_e_table" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "ai_id" UNIQUE ("id" ASC)
);
INSERT INTO ai_table VALUES(1,1,0,'分析结果：电压波动15%可能由电源输入不稳定、负载突变或线路接触不良引起。虽然当前波动幅度未达到严重故障标准，但长期存在可能影响电气元件寿命和电梯运行稳定性。','警告');
INSERT INTO ai_table VALUES(2,2,0,'分析结果：导轨垂直度偏差值为0.8mm，根据电梯维护标准，导轨垂直度偏差通常允许范围在0.5mm至1.0mm之间。当前值处于允许范围内，但接近上限，可能影响电梯运行的平稳性和舒适性。建议进一步检查导轨安装固定情况，排除因安装松动或基础沉降导致的偏差增大。','警告');
INSERT INTO ai_table VALUES(3,3,0,'分析结果：电压波动15%属于轻微波动，可能由电网不稳定或设备负载变化引起。虽然当前波动在允许范围内，但长期存在可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。','警告');
INSERT INTO ai_table VALUES(4,4,1,'分析结果：电机温度过高，可能由电机过载、散热不良或轴承润滑不足引起。90°C已超过正常工作温度范围（通常应≤75°C），存在烧毁绕组或加速绝缘老化的风险。','严重故障');
INSERT INTO ai_table VALUES(5,5,0,'分析结果：导轨垂直度偏差当前值为0.8mm，根据标准，导轨垂直度偏差应控制在0.5mm以内。当前偏差虽超出标准，但未达到严重故障级别。可能原因包括导轨安装不精确、长期使用导致的微小变形或固定螺栓松动。','警告');
INSERT INTO ai_table VALUES(6,6,0,'分析结果：电压波动15%属于警告级别，可能由电源不稳定、负载突变或线路接触不良引起。虽然当前未达到严重故障标准，但持续波动可能影响电梯控制系统的稳定性和电气元件寿命。','警告');
INSERT INTO ai_table VALUES(7,7,0,'分析结果：电压波动15%属于轻微异常，可能由电网波动或电源模块老化引起。虽然当前未达到严重故障标准，但长期波动可能导致电气元件寿命缩短或控制系统不稳定。建议检查电源输入稳定性及滤波电路状态。','警告');
INSERT INTO ai_table VALUES(8,8,0,'分析结果：电压波动15%属于轻微波动，可能由电网波动或局部负载变化引起。虽然未达到紧急阈值，但长期存在可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。','警告');
INSERT INTO ai_table VALUES(9,9,0,'分析结果：电压波动15%属于轻微波动，可能由电网波动或局部负载变化引起。虽然未达到严重故障标准，但长期波动可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。','警告');
INSERT INTO ai_table VALUES(10,10,0,'分析结果：开关门时间检测值为4.5秒，可能由于门机系统运行速度异常或门轨道阻力增大导致。需检查门机电机、传动装置及门轨道是否顺畅。','警告');
INSERT INTO ai_table VALUES(11,11,1,'分析结果：电机温度达到90°C，属于严重过热状态，可能由电机过载、散热不良或轴承润滑不足引起。高温会导致绝缘材料老化，严重时可能引发电机烧毁或火灾。','严重故障');
INSERT INTO ai_table VALUES(12,12,0,'分析结果：开关门时间检测值为4.5秒，该值在正常范围内（通常为3-5秒），但接近上限。可能原因包括门机皮带松弛、导轨润滑不足或门机控制系统响应略有延迟。建议进一步检查门机运行状态以确保无潜在问题。','警告');
INSERT INTO ai_table VALUES(13,13,1,'分析结果：电机温度达到90°C，属于严重过热状态。可能原因包括电机过载、散热不良、轴承磨损或润滑不足。高温会导致绝缘材料老化，严重时可能引发电机烧毁或火灾。','严重故障');
INSERT INTO ai_table VALUES(14,14,0,'分析结果：电压波动值为15%，属于轻微波动范围，可能由电网波动或局部负载变化引起。虽然当前未达到严重故障标准，但长期存在可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。','警告');
INSERT INTO ai_table VALUES(15,15,0,'分析结果：电压波动15%属于轻微波动，可能由电网波动或局部负载变化引起。虽然未达到严重故障标准，但长期波动可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。','警告');
INSERT INTO ai_table VALUES(16,16,0,'分析结果：开关门时间检测值为4.5秒，略高于标准值（通常为3-4秒），可能由门机传动部件磨损、门轨润滑不足或门机控制系统响应延迟引起。需进一步检查门机运行状态。','警告');
INSERT INTO ai_table VALUES(17,17,0,'分析结果：电压波动15%可能由电源不稳定、线路接触不良或负载突变引起。虽然未达到严重故障标准，但长期波动可能影响电气元件寿命和电梯运行稳定性。','警告');
INSERT INTO ai_table VALUES(18,18,1,'分析结果：电机温度达到90°C，属于严重过热状态，可能由电机过载、散热不良或轴承润滑不足引起。长期高温运行会导致绝缘老化、效率下降甚至烧毁电机。','严重故障');
INSERT INTO ai_table VALUES(19,19,0,'分析结果：开关门时间为4.5秒，可能由于门机系统响应延迟或机械部件磨损导致。虽然当前值在允许范围内，但接近上限，建议进一步检查门机运行状态。','警告');
INSERT INTO ai_table VALUES(20,20,1,'分析结果：电机温度达到90°C，属于严重过热状态。可能原因包括：电机轴承磨损、冷却系统故障、负载过大或电源电压异常。持续高温运行会导致绝缘老化、效率下降甚至电机烧毁。','严重故障');
INSERT INTO ai_table VALUES(21,21,0,'分析结果：导轨垂直度偏差值为0.8mm，根据行业标准，垂直度偏差在1mm以内属于允许范围。当前偏差值虽未超标，但接近临界值，可能由安装误差或长期运行震动导致。建议进行预防性检查以避免偏差进一步扩大。','警告');
CREATE TABLE IF NOT EXISTS "users" (
  "id" INTEGER NOT NULL,
  "user_name" TEXT NOT NULL,
  "user_phone" TEXT,
  "position" TEXT,
  "role" TEXT,
  "email" TEXT,
  "password" TEXT,
  "salt" TEXT,
  "condition" TEXT DEFAULT 空闲,
  PRIMARY KEY ("id")
);
INSERT INTO users VALUES(1002,'王大锤','155555558791','维护人员','maintenance','1234566@qq.com','1234566',NULL,'忙碌');
INSERT INTO users VALUES(1006,'胡佳佳','1967658813791','技术人员','maintenance','1234567@qq.com','1234567',NULL,'忙碌');
INSERT INTO users VALUES(10011,'李芯湉','1875357813791','维护人员','maintenance','1234568@qq.com','1234568',NULL,'忙碌');
INSERT INTO users VALUES(10091,'胡佳佳','19676581234','维护人员','maintenance','1234569@qq.com','1234569',NULL,'空闲');
INSERT INTO users VALUES(10092,'胡','19676581234','特勤人员','admin','admin123@qq.com','admin123',NULL,'空闲');
INSERT INTO users VALUES(10093,'熊大','15808067892','特勤人员','maintenance','33355@qq.com','1234567899',NULL,'空闲');
INSERT INTO users VALUES(10096,'熊二','15808067892','管理人员','admin','123@qq.com','f9bcb87d6db91740720b5cfaefbb11ba3f9555e1','d007aa9f35c66c0ceedd6afd59e20dca','空闲');
INSERT INTO users VALUES(10098,'熊三','158082366549','维护人员','maintenance','33333@qq.com','170dd71a2417810b65f60360411920f0af64a728','f1f124d4f2277d4bf1522ff4b290988d','空闲');
INSERT INTO users VALUES(10099,'熊四','1544694619555','待分配','admin','56789@qq.com','f942e1085098f0f5ce08c0a1d65c7b578c7d72a5','ca935da65c79b0e696bad5f312d3ce86','空闲');
INSERT INTO users VALUES(10100,'陈呀','17827833','维护人员','user','33740@qq.com','48f9cbcc698d95531a97abfd6090db6fed53caf0','9161784b90b40f31bf1feae43cda80cb','空闲');
CREATE TABLE IF NOT EXISTS "maintain_table" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "mt_time" TEXT(255),
  "user_id" INTEGER,
  "mt_data_id" INTEGER,
  "status" TEXT(255),
  "remark" TEXT(255),
  "sum" INTEGER,
  "descr" TEXT,
  CONSTRAINT "mt_data_id" FOREIGN KEY ("mt_data_id") REFERENCES "data_e_table" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "user_id" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO maintain_table VALUES(1,'2025-07-10T15:57:35.479029800',1002,1,'已维护','AI检测到分析结果：电压波动15%可能由电源输入不稳定、负载突变或线路接触不良引起。虽然当前波动幅度未达到严重故障标准，但长期存在可能影响电气元件寿命和电梯运行稳定性。，分配给王大锤处理',1,NULL);
INSERT INTO maintain_table VALUES(2,'2025-07-10T15:57:34.040049600',10091,2,'已维护','AI检测到分析结果：导轨垂直度偏差值为0.8mm，根据电梯维护标准，导轨垂直度偏差通常允许范围在0.5mm至1.0mm之间。当前值处于允许范围内，但接近上限，可能影响电梯运行的平稳性和舒适性。建议进一步检查导轨安装固定情况，排除因安装松动或基础沉降导致的偏差增大。，分配给胡佳佳处理',1,NULL);
INSERT INTO maintain_table VALUES(3,'2025-07-10T15:57:06.251470100',1006,3,'已维护','AI检测到分析结果：电压波动15%属于轻微波动，可能由电网不稳定或设备负载变化引起。虽然当前波动在允许范围内，但长期存在可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。，分配给胡佳佳处理',1,NULL);
INSERT INTO maintain_table VALUES(4,'2025-07-10T16:33:13.202533900',10011,4,'已维护','AI检测到分析结果：电机温度过高，可能由电机过载、散热不良或轴承润滑不足引起。90°C已超过正常工作温度范围（通常应≤75°C），存在烧毁绕组或加速绝缘老化的风险。，分配给李芯湉处理',1,NULL);
INSERT INTO maintain_table VALUES(5,'2025-07-11T11:44:12.473622800',10098,5,'已维护','AI检测到分析结果：导轨垂直度偏差当前值为0.8mm，根据标准，导轨垂直度偏差应控制在0.5mm以内。当前偏差虽超出标准，但未达到严重故障级别。可能原因包括导轨安装不精确、长期使用导致的微小变形或固定螺栓松动。，分配给熊三处理',1,NULL);
INSERT INTO maintain_table VALUES(6,'2025-07-11T11:48:07.888395700',10098,7,'已维护','AI检测到分析结果：电压波动15%属于轻微异常，可能由电网波动或电源模块老化引起。虽然当前未达到严重故障标准，但长期波动可能导致电气元件寿命缩短或控制系统不稳定。建议检查电源输入稳定性及滤波电路状态。，分配给熊三处理',1,'hahahahah');
INSERT INTO maintain_table VALUES(7,'2025-07-11T12:17:28.112129200',10011,8,'待处理','AI检测到分析结果：电压波动15%属于轻微波动，可能由电网波动或局部负载变化引起。虽然未达到紧急阈值，但长期存在可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。，分配给李芯湉处理',0,NULL);
INSERT INTO maintain_table VALUES(8,'2025-07-11T12:19:36.226757700',10098,10,'已维护','AI检测到分析结果：开关门时间检测值为4.5秒，可能由于门机系统运行速度异常或门轨道阻力增大导致。需检查门机电机、传动装置及门轨道是否顺畅。，分配给熊三处理',1,'3421111111111111');
INSERT INTO maintain_table VALUES(9,'2025-07-11T13:03:55.932751800',10098,11,'已维护','AI检测到分析结果：电机温度达到90°C，属于严重过热状态，可能由电机过载、散热不良或轴承润滑不足引起。高温会导致绝缘材料老化，严重时可能引发电机烧毁或火灾。，分配给熊三处理',1,NULL);
INSERT INTO maintain_table VALUES(10,'2025-07-11T13:05:20.693347100',10098,12,'已维护','AI检测到分析结果：开关门时间检测值为4.5秒，该值在正常范围内（通常为3-5秒），但接近上限。可能原因包括门机皮带松弛、导轨润滑不足或门机控制系统响应略有延迟。建议进一步检查门机运行状态以确保无潜在问题。，分配给熊三处理',1,'已完成');
INSERT INTO maintain_table VALUES(11,'2025-07-11T13:30:16.380402400',10098,14,'已维护','AI检测到分析结果：电压波动值为15%，属于轻微波动范围，可能由电网波动或局部负载变化引起。虽然当前未达到严重故障标准，但长期存在可能影响电气元件寿命。建议检查电源输入稳定性及负载均衡情况。，分配给熊三处理',1,'1234');
INSERT INTO maintain_table VALUES(12,'2025-07-11T13:43:27.658430100',10098,17,'已维护','AI检测到分析结果：电压波动15%可能由电源不稳定、线路接触不良或负载突变引起。虽然未达到严重故障标准，但长期波动可能影响电气元件寿命和电梯运行稳定性。，分配给熊三处理',1,'1213132');
INSERT INTO maintain_table VALUES(13,'2025-07-12T11:59:31.410662500',10098,18,'已维护','AI检测到分析结果：电机温度达到90°C，属于严重过热状态，可能由电机过载、散热不良或轴承润滑不足引起。长期高温运行会导致绝缘老化、效率下降甚至烧毁电机。，分配给熊三处理',1,'12124312412');
INSERT INTO maintain_table VALUES(14,'2025-07-12T13:25:49.042325300',10098,19,'已维护','AI检测到分析结果：开关门时间为4.5秒，可能由于门机系统响应延迟或机械部件磨损导致。虽然当前值在允许范围内，但接近上限，建议进一步检查门机运行状态。，分配给熊三处理',1,'121231234');
INSERT INTO maintain_table VALUES(15,'2025-07-12T13:48:55.044150300',10098,20,'已维护','AI检测到分析结果：电机温度达到90°C，属于严重过热状态。可能原因包括：电机轴承磨损、冷却系统故障、负载过大或电源电压异常。持续高温运行会导致绝缘老化、效率下降甚至电机烧毁。，分配给熊三处理',1,'已完成');
INSERT INTO maintain_table VALUES(16,'2025-07-13T15:50:38.133265800',10098,21,'已维护','AI检测到分析结果：导轨垂直度偏差值为0.8mm，根据行业标准，垂直度偏差在1mm以内属于允许范围。当前偏差值虽未超标，但接近临界值，可能由安装误差或长期运行震动导致。建议进行预防性检查以避免偏差进一步扩大。，分配给熊三处理',1,'已完成');
DELETE FROM sqlite_sequence;
INSERT INTO sqlite_sequence VALUES('data_e_table',122);
INSERT INTO sqlite_sequence VALUES('ai_table',43);
INSERT INTO sqlite_sequence VALUES('maintain_table',16);
CREATE UNIQUE INDEX "data_e_table_id_uindex"
ON "data_e_table" (
  "id" ASC
);
COMMIT;
