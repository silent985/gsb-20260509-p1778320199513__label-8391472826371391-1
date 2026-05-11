package com.staffsync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 员工管理系统主启动类
 */
@SpringBootApplication
@MapperScan("com.staffsync.mapper")
public class StaffSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffSyncApplication.class, args);
    }
}
