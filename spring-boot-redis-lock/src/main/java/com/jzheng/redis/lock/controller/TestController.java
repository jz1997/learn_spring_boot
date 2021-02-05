package com.jzheng.redis.lock.controller;

import com.jzheng.redis.lock.lock.RedisLock;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController: 测试
 *
 * @author jzheng
 * @since 2021/1/31 4:09 下午
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping
    public void testRedisLock() {

        RedisLock redisLock = RedisLock.of(redisTemplate, 3L, TimeUnit.SECONDS);
        redisLock.lock();

        // 业务逻辑
        Object num = redisTemplate.opsForValue().get("num");
        if (num != null) {
            System.out.println(num);
            redisTemplate.opsForValue().set("num", ((int) num - 1));
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        redisLock.unlock();
    }

}
