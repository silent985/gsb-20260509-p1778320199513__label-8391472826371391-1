# Staff Sync - 员工管理系统

一个基于 Spring Boot + Vue 3 的全栈员工管理系统，支持用户注册登录与员工信息的增删改查，采用 Docker 一键交付。

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 前端 | Vue 3 + Vite + Tailwind CSS |
| 后端 | Spring Boot 3.x + MyBatis-Plus |
| 认证 | JWT (JSON Web Token) |
| 数据库 | MySQL 8.0 |
| 容器化 | Docker + Docker Compose |

## 🚀 快速启动 (Docker)

1. 确保 Docker Desktop 已运行  
2. 在根目录执行：
```bash
docker compose up --build
```
3. 访问应用：
   - 前端：http://localhost:3000  
   - 后端 API：http://localhost:8000  
   - Hello World：http://localhost:8000/

## 🧪 测试账号

| 用户名 | 密码 |
|--------|------|
| admin | 123456 |

> 注：也可以通过注册页面创建新账号

## 📸 功能介绍

### 登录注册
- 用户注册（用户名、密码）
- 用户登录（JWT 认证）
- 登出功能

### 员工管理
- 员工列表展示
- 新增员工（姓名、手机号、性别）
- 编辑员工信息
- 删除员工
- 员工统计（总数、男女比例）

## 📚 项目文档

`docs/` 目录包含完整的架构、设计、开发、测试和用户手册：
- [docs/architecture.md](docs/architecture.md)：系统架构说明  
- [docs/design.md](docs/design.md)：设计规范与页面说明  
- [docs/development.md](docs/development.md)：开发与配置说明  
- [docs/testing.md](docs/testing.md)：测试用例与验证步骤  
- [docs/user_manual.md](docs/user_manual.md)：用户使用手册  
- [docs/task.md](docs/task.md)：任务清单  
- [docs/implementation_plan.md](docs/implementation_plan.md)：实施计划  
- [docs/walkthrough.md](docs/walkthrough.md)：功能验证记录  

## 📁 项目结构

```
staff-sync/
├── README.md           # 项目说明
├── docker-compose.yml  # Docker 编排
├── init.sql            # 数据库初始化
├── backend/            # 后端 Spring Boot
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/staffsync/
│       ├── StaffSyncApplication.java  # 主启动类
│       ├── controller/                # 控制器层
│       ├── service/                   # 服务层
│       ├── mapper/                    # 数据访问层
│       ├── model/                     # 实体类
│       ├── dto/                       # 数据传输对象
│       ├── config/                    # 配置类
│       ├── common/                    # 通用类
│       ├── util/                      # 工具类
│       └── exception/                 # 异常处理
└── frontend/           # 前端 Vue 3
    ├── Dockerfile
    ├── nginx.conf
    ├── package.json
    └── src/
        ├── main.js          # 入口文件
        ├── App.vue          # 根组件
        ├── router/          # 路由配置
        ├── stores/          # 状态管理
        ├── api/             # API 封装
        ├── pages/           # 页面组件
        └── components/      # 通用组件
```

## 🔌 API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/` | Hello World |
| POST | `/api/auth/register` | 用户注册 |
| POST | `/api/auth/login` | 用户登录 |
| GET | `/api/employees` | 获取员工列表 |
| POST | `/api/employees` | 新增员工 |
| PUT | `/api/employees/{id}` | 更新员工 |
| DELETE | `/api/employees/{id}` | 删除员工 |

## 🎨 UI 特性

- ✅ Tailwind CSS 现代设计
- ✅ 渐变背景与卡片式布局
- ✅ 按钮 Hover 动画
- ✅ Loading 加载动画
- ✅ Toast 消息提示 (美化升级：毛玻璃效果 + 顶部悬浮)
- ✅ 智能交互 (Confirm 模态框 + 表单自动去空)
- ✅ 移动端响应式适配

## 📋 端口说明

| 服务 | 端口 |
|------|------|
| 前端 | 3000 |
| 后端 | 8000 |
| MySQL | 3306 |
