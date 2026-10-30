-- =============================================
-- 班级学生成绩管理系统 数据库初始化脚本
-- 数据库: MySQL 8.0+
-- =============================================

CREATE DATABASE IF NOT EXISTS grade_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE grade_system;

-- ----------------------------
-- 1. 用户表（教师/学生统一账号）
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  username    VARCHAR(50)  NOT NULL UNIQUE COMMENT '登录账号',
  password    VARCHAR(64)  NOT NULL COMMENT '密码（MD5加密）',
  real_name   VARCHAR(50)  NOT NULL COMMENT '真实姓名',
  role        VARCHAR(20)  NOT NULL COMMENT '角色：TEACHER-教师 STUDENT-学生',
  create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_username (username)
) ENGINE=InnoDB COMMENT='系统用户表';

-- ----------------------------
-- 2. 班级表
-- ----------------------------
DROP TABLE IF EXISTS class_info;
CREATE TABLE class_info (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  class_name  VARCHAR(50)  NOT NULL COMMENT '班级名称',
  grade       VARCHAR(20)  NOT NULL COMMENT '年级',
  create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB COMMENT='班级信息表';

-- ----------------------------
-- 3. 课程表
-- ----------------------------
DROP TABLE IF EXISTS course;
CREATE TABLE course (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  course_name  VARCHAR(50)  NOT NULL COMMENT '课程名称',
  teacher_id   BIGINT       NOT NULL COMMENT '授课教师ID（关联sys_user）',
  create_time  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_teacher (teacher_id)
) ENGINE=InnoDB COMMENT='课程表';

-- ----------------------------
-- 4. 学生表
-- ----------------------------
DROP TABLE IF EXISTS student;
CREATE TABLE student (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  student_no  VARCHAR(30)  NOT NULL UNIQUE COMMENT '学号',
  name        VARCHAR(50)  NOT NULL COMMENT '姓名',
  class_id    BIGINT       NOT NULL COMMENT '所属班级ID',
  user_id     BIGINT       COMMENT '关联系统用户ID（学生登录账号）',
  create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_class (class_id),
  INDEX idx_user (user_id)
) ENGINE=InnoDB COMMENT='学生信息表';

-- ----------------------------
-- 5. 成绩表
-- ----------------------------
DROP TABLE IF EXISTS score;
CREATE TABLE score (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
  student_id  BIGINT       NOT NULL COMMENT '学生ID',
  course_id   BIGINT       NOT NULL COMMENT '课程ID',
  exam_name   VARCHAR(50)  NOT NULL COMMENT '考试名称（如：第一次月考）',
  score       DECIMAL(5,2) NOT NULL COMMENT '分数（0-100）',
  create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  -- 同一学生同一课程同一考试只能有一条成绩
  UNIQUE KEY uk_student_course_exam (student_id, course_id, exam_name),
  INDEX idx_student (student_id),
  INDEX idx_course (course_id)
) ENGINE=InnoDB COMMENT='成绩表';

-- =============================================
-- 模拟数据
-- =============================================

-- 班级（2个班）
INSERT INTO class_info (class_name, grade) VALUES
('计算机1班', '2023级'),
('计算机2班', '2023级');

-- 用户（2个教师 + 4个学生示例，实际学生账号可批量生成）
-- 密码统一为 123456 的 MD5：e10adc3949ba59abbe56e057f20f883e
INSERT INTO sys_user (username, password, real_name, role) VALUES
('teacher01', 'e10adc3949ba59abbe56e057f20f883e', '张老师', 'TEACHER'),
('teacher02', 'e10adc3949ba59abbe56e057f20f883e', '李老师', 'TEACHER'),
('student01', 'e10adc3949ba59abbe56e057f20f883e', '王小明', 'STUDENT'),
('student02', 'e10adc3949ba59abbe56e057f20f883e', '李小红', 'STUDENT'),
('student03', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'STUDENT'),
('student04', 'e10adc3949ba59abbe56e057f20f883e', '李四', 'STUDENT'),
('student05', 'e10adc3949ba59abbe56e057f20f883e', '王五', 'STUDENT'),
('student06', 'e10adc3949ba59abbe56e057f20f883e', '赵六', 'STUDENT'),
('student07', 'e10adc3949ba59abbe56e057f20f883e', '钱七', 'STUDENT'),
('student08', 'e10adc3949ba59abbe56e057f20f883e', '孙八', 'STUDENT'),
('student09', 'e10adc3949ba59abbe56e057f20f883e', '周九', 'STUDENT'),
('student10', 'e10adc3949ba59abbe56e057f20f883e', '吴十', 'STUDENT');

-- 课程（3门课，2个教师授课）
INSERT INTO course (course_name, teacher_id) VALUES
('Java程序设计', 1),
('数据库原理',   1),
('数据结构',     2);

-- 学生（1班6人，2班4人）
INSERT INTO student (student_no, name, class_id, user_id) VALUES
('2023001', '王小明', 1, 3),
('2023002', '李小红', 1, 4),
('2023003', '张三',   1, 5),
('2023004', '李四',   1, 6),
('2023005', '王五',   1, 7),
('2023006', '赵六',   1, 8),
('2023007', '钱七',   2, 9),
('2023008', '孙八',   2, 10),
('2023009', '周九',   2, 11),
('2023010', '吴十',   2, 12);

-- 成绩（3门课 × 2次考试 × 10学生 = 60条）
-- 第一次月考
INSERT INTO score (student_id, course_id, exam_name, score) VALUES
(1,1,'第一次月考',85.5),(1,2,'第一次月考',78.0),(1,3,'第一次月考',92.0),
(2,1,'第一次月考',90.0),(2,2,'第一次月考',88.5),(2,3,'第一次月考',76.0),
(3,1,'第一次月考',56.0),(3,2,'第一次月考',62.0),(3,3,'第一次月考',58.0),
(4,1,'第一次月考',72.0),(4,2,'第一次月考',68.0),(4,3,'第一次月考',75.0),
(5,1,'第一次月考',88.0),(5,2,'第一次月考',82.0),(5,3,'第一次月考',90.0),
(6,1,'第一次月考',45.0),(6,2,'第一次月考',52.0),(6,3,'第一次月考',48.0),
(7,1,'第一次月考',78.0),(7,2,'第一次月考',80.0),(7,3,'第一次月考',85.0),
(8,1,'第一次月考',65.0),(8,2,'第一次月考',70.0),(8,3,'第一次月考',68.0),
(9,1,'第一次月考',95.0),(9,2,'第一次月考',91.0),(9,3,'第一次月考',93.0),
(10,1,'第一次月考',58.0),(10,2,'第一次月考',55.0),(10,3,'第一次月考',60.0);

-- 第二次月考
INSERT INTO score (student_id, course_id, exam_name, score) VALUES
(1,1,'第二次月考',88.0),(1,2,'第二次月考',82.0),(1,3,'第二次月考',95.0),
(2,1,'第二次月考',92.0),(2,2,'第二次月考',90.0),(2,3,'第二次月考',80.0),
(3,1,'第二次月考',60.0),(3,2,'第二次月考',65.0),(3,3,'第二次月考',62.0),
(4,1,'第二次月考',75.0),(4,2,'第二次月考',72.0),(4,3,'第二次月考',78.0),
(5,1,'第二次月考',90.0),(5,2,'第二次月考',85.0),(5,3,'第二次月考',92.0),
(6,1,'第二次月考',50.0),(6,2,'第二次月考',58.0),(6,3,'第二次月考',55.0),
(7,1,'第二次月考',82.0),(7,2,'第二次月考',85.0),(7,3,'第二次月考',88.0),
(8,1,'第二次月考',70.0),(8,2,'第二次月考',72.0),(8,3,'第二次月考',75.0),
(9,1,'第二次月考',96.0),(9,2,'第二次月考',93.0),(9,3,'第二次月考',95.0),
(10,1,'第二次月考',62.0),(10,2,'第二次月考',60.0),(10,3,'第二次月考',65.0);

-- =============================================
-- 查询验证
-- =============================================
-- 测试账号：
--   教师: teacher01 / 123456  (张老师，教Java和数据库)
--   教师: teacher02 / 123456  (李老师，教数据结构)
--   学生: student01 / 123456  (王小明，1班)
--   学生: student07 / 123456  (钱七，2班)
