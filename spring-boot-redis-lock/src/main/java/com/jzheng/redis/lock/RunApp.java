package com.jzheng.redis.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RunApp: Redis 单机分布式锁ß
 *
 * @author jzheng
 * @since 2021/1/31 4:02 下午
 */
@SpringBootApplication
public class RunApp {
    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
    }
}
