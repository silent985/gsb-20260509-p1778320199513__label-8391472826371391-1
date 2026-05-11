package com.staffsync.service;

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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        username = username.trim();
        // 检查用户名是否已存在（忽略前后空格）
        if (userMapper.countByUsernameTrim(username) > 0) {
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
        username = username.trim();
        // 查找所有匹配的用户（忽略前后空格）
        List<User> users = userMapper.selectListByUsernameTrim(username);

        if (users.isEmpty()) {
            return Result.error("用户名或密码错误");
        }

        // 如果有多个只差前后空格的账号，明确拦截提示
        if (users.size() > 1) {
            String duplicateUsernames = users.stream()
                    .map(u -> "\"" + u.getUsername() + "\"")
                    .collect(Collectors.joining(", "));
            return Result.error("检测到多个用户名只差前后空格的账号：" + duplicateUsernames + "，请联系管理员处理");
        }

        User user = users.get(0);

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
