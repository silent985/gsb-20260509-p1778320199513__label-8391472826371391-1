# 系统架构说明

## 1. 架构概览
本项目为典型的前后端分离架构，整体部署通过 Docker Compose 统一编排。

- 前端：Vue 3 + Vite + Tailwind CSS（静态资源由 Nginx 托管）
- 后端：Spring Boot 3.x + MyBatis-Plus（REST API + JWT 认证）
- 数据库：MySQL 8.0（持久化卷）


## 2. 模块划分

### 前端模块
- `pages/`：页面级组件（Login/Register/Dashboard）
- `components/`：通用组件（EmployeeForm/ConfirmModal/Toast）
- `stores/`：Pinia 状态管理（auth/toast）
- `api/`：Axios 请求封装与拦截器
- `router/`：路由守卫与访问控制

### 后端模块
- `controller/`：HTTP 接口入口（Auth/Employee/Home）
- `service/`：业务逻辑（认证/员工管理）
- `mapper/`：数据访问（MyBatis-Plus）
- `model/`：实体模型（User/Employee）
- `dto/`：请求参数对象（Login/Register）
- `config/`：跨域/JWT 拦截/MP 配置
- `exception/`：全局异常处理
- `common/`：统一返回体封装

## 3. 关键数据流

### 登录流程
1. 用户提交用户名/密码 → `POST /api/auth/login`
2. 后端校验成功 → 返回 `token + username`
3. 前端保存 token → 路由守卫允许访问 `/dashboard`

### 员工新增流程
1. 前端表单校验 → `POST /api/employees`
2. 后端参数校验与保存 → 返回统一结果
3. 前端提示 Toast → 刷新列表

## 4. 安全与异常
- JWT 拦截：`/api/**` 默认校验，登录/注册放行
- 参数校验：`@Valid` + 全局异常处理器
- 统一响应：`Result` 结构（code/message/data）

## 5. 端口与网络
- 前端：3000（宿主机/容器）
- 后端：8000（宿主机/容器）
- MySQL：3306（宿主机/容器）

