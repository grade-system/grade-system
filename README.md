# 班级学生成绩管理系统

基于 JavaEE（Servlet + JSP + JDBC）技术栈开发的班级学生成绩管理系统，支持教师录入与管理成绩、多维度统计分析，学生查询个人成绩与成绩趋势。

## 技术栈

- 后端：Servlet 4.0、JSP、JSTL 1.2、JDBC（简易连接池）
- 数据库：MySQL 8.0
- 前端：JSP + CSS + JavaScript + ECharts（本地引用）
- 构建工具：Maven（Jetty Maven Plugin 内嵌运行）
- 密码存储：MD5 加密

## 功能列表

### 基础功能

- 用户登录 / 退出，教师与学生角色权限隔离（`/teacher/*`、`/student/*` 路径分别拦截）
- 教师端：班级管理、课程管理、学生管理、成绩录入与查询
- 学生端：个人成绩查询
- 统计功能：平均分、及格率

### 亮点功能

- **标准差**：衡量班级成绩离散程度
- **优秀率**（85 分及以上占比）
- **临界生分析**：筛选 55~59 分区间的学生，辅助教师重点关注
- **学生成绩趋势图**：按课程展示历次考试成绩变化（ECharts 折线图）

## 部署步骤

### 1. 环境要求

- JDK 8 或以上
- Maven 3.6 或以上
- MySQL 8.0（确保服务已启动）

### 2. 初始化数据库

使用 MySQL 客户端执行项目中的建库建表脚本：

```sql
source sql/init.sql
```

该脚本会创建 `grade_system` 数据库、5 张表（用户、班级、课程、学生、成绩）以及模拟测试数据。

### 3. 配置数据库连接

修改 `src/main/resources/db.properties`，将用户名和密码改为本机 MySQL 的实际账号：

```properties
db.username=root
db.password=你的密码
```

### 4. 启动项目

在项目根目录执行：

```bash
mvn jetty:run
```

控制台出现 `Started Jetty Server` 即启动成功。

### 5. 访问系统

浏览器访问：**http://localhost:8081/grade-system/**

### 6. 停止服务

在控制台按 `Ctrl + C`。

## 测试账号

| 角色 | 账号 | 密码 | 说明 |
| ---- | ---- | ---- | ---- |
| 教师 | teacher01 | 123456 | 张老师，教授 1 班 |
| 教师 | teacher02 | 123456 | 李老师，教授 2 班 |
| 学生 | student01 | 123456 | 王小明，1 班 |
| 学生 | student07 | 123456 | 钱七，2 班 |

## 项目结构

```
grade-system
├── sql
│   └── init.sql                  # 建库建表 + 模拟数据
├── src/main
│   ├── java/com/grade
│   │   ├── util                  # DBUtil（连接池）、Md5Util、PageUtil
│   │   ├── filter                # 编码过滤器、登录/权限拦截器
│   │   ├── entity                # 数据库实体类
│   │   ├── dto                   # 统计数据传输对象
│   │   ├── dao                   # 数据访问层（PreparedStatement 防注入）
│   │   ├── service               # 业务逻辑层
│   │   └── servlet               # 控制器层（登录、CRUD、成绩、统计）
│   ├── resources
│   │   └── db.properties         # 数据库连接配置
│   └── webapp
│       ├── WEB-INF/web.xml       # Servlet 与过滤器注册
│       ├── login.jsp             # 登录页
│       ├── common                # 公共 JSP（头部、导航）
│       ├── teacher               # 教师端页面
│       ├── student               # 学生端页面
│       └── static                # CSS / JS / ECharts（本地化）
└── pom.xml                       # Maven 配置（Jetty 插件，端口 8081）
```

## 设计要点

- 所有数据库操作使用 `PreparedStatement` 占位符，防止 SQL 注入
- 成绩表设有（学生 + 课程 + 考试名称）唯一约束，防止重复录入
- JSP 仅负责视图渲染，统一使用 JSTL + EL 表达式，业务数据由 Servlet 通过 request 域传递
- 全局 `EncodingFilter` 统一处理 UTF-8 编码
- 教师只能查看和管理自己所授班级、课程的数据，数据隔离在 SQL 层完成
