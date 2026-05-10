package com.staffsync.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 */
@RestController
public class HomeController {

    /**
     * Hello World接口
     */
    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
