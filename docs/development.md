# 开发说明

## 1. 技术栈
- 前端：Vue 3 + Vite + Tailwind CSS + Pinia + Axios
- 后端：Spring Boot 3.x + MyBatis-Plus + JWT
- 数据库：MySQL 8.0
- 部署：Docker + Docker Compose

## 2. 目录结构

```
frontend/
  src/
    api/            # API 封装
    components/     # 组件
    pages/          # 页面
    router/         # 路由
    stores/         # 状态管理
    style.css       # 全局样式
backend/
  src/main/java/com/staffsync/
    controller/     # 控制器
    service/        # 服务
    mapper/         # Mapper
    model/          # 实体
    dto/            # DTO
    config/         # 配置
    exception/      # 异常处理
    common/         # 通用返回
  src/main/resources/
    application.yml # 配置
init.sql            # 初始化数据
```

## 3. 接口约定
- 统一返回结构：`{ code, message, data }`
- 认证方式：Bearer Token（`Authorization: Bearer <token>`）

## 4. 关键配置
- 后端端口：8000
- 数据库：MySQL（字符集 utf8mb4）
- JWT 有效期：24 小时

## 5. 启动方式（仅 Docker）
```bash
docker compose up --build
```

## 6. 常见问题
- 登录后 401：检查 token 是否过期或未携带
- 前端 API 失败：确认 `nginx.conf` 代理是否指向 `backend:8000`

