package com.staffsync.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.staffsync.common.Result;
import com.staffsync.mapper.UserMapper;
import com.staffsync.model.User;
import com.staffsync.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    /**
     * 用户注册
     */
    public Result<Void> register(String username, String password) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        if (userMapper.selectCount(wrapper) > 0) {
            return Result.error("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptPassword(password));
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);

        return Result.success("注册成功", null);
    }

    /**
     * 用户登录
     */
    public Result<Map<String, Object>> login(String username, String password) {
        // 查找用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 验证密码
        if (!user.getPassword().equals(encryptPassword(password))) {
            return Result.error("用户名或密码错误");
        }

        // 生成Token
        String token = jwtUtils.generateToken(user.getId(), user.getUsername());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("username", user.getUsername());

        return Result.success("登录成功", data);
    }

    /**
     * 密码加密
     */
    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(("staffsync_" + password).getBytes(StandardCharsets.UTF_8));
    }
}
