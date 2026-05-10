-- 员工管理系统数据库初始化脚本
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（MD5加密）',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建员工表
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '员工ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) NOT NULL COMMENT '手机号',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- 插入测试用户 (密码: 123456, 使用staffsync_123456的MD5)
INSERT INTO users (username, password, created_at) VALUES 
('admin', 'd104fd693083da4ff5fd9ea554a16d59', NOW());

-- 插入测试员工数据
INSERT INTO employees (name, phone, gender, created_at, updated_at) VALUES 
('张三', '13800138001', '男', NOW(), NOW()),
('李四', '13800138002', '女', NOW(), NOW()),
('王五', '13800138003', '男', NOW(), NOW());
