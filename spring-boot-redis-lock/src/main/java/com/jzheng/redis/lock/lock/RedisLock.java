package com.jzheng.redis.lock.lock;


import com.jzheng.redis.lock.common.RedisLockConst;
import com.jzheng.redis.lock.util.SpringUtils;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * RedisLock: redis 实现分布式锁
 *
 * @author jzheng
 * @since 2021/1/31 10:30 下午
 */
@Slf4j
public class RedisLock {

    /**
     * 锁记录的值 进行解锁用的
     */
    private String lockValue;

    /**
     * 操作 redis 的工具
     */
    private final RedisTemplate<Object, Object> redisTemplate;

    /**
     * 过期时间
     */
    private final Long expireTime;

    /**
     * 过期时间单位
     */
    private final TimeUnit timeUnit;

    /**
     * 线程池
     */
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;


    /**
     * 私有化构造方法，统一通过 of 方法进行创建对象
     * @param redisTemplate {@link RedisTemplate}
     * @param expireTime    过期时间
     * @param timeUnit      {@link TimeUnit} 时间单位
     */
    private RedisLock(RedisTemplate<Object, Object> redisTemplate, Long expireTime,
        TimeUnit timeUnit) {
        this.redisTemplate = redisTemplate;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;

        threadPoolTaskExecutor = SpringUtils
            .getBean("taskExecutor", ThreadPoolTaskExecutor.class);
    }

    /**
     * 创建 redis lock
     *
     * @param redisTemplate redisTemplate
     * @param expireTime    过期时间
     * @param timeUnit      过期时间的单位
     * @return 创建好的 redis lock
     */
    public static RedisLock of(RedisTemplate<Object, Object> redisTemplate, Long expireTime,
        TimeUnit timeUnit) {
        return new RedisLock(redisTemplate, expireTime, timeUnit);
    }


    /**
     * 进行上锁
     *
     * @return lockValue 返回锁的值，后面进行时间更新
     */
    @SuppressWarnings("all")
    public String lock() {

        lockValue = UUID.randomUUID().toString();

        // 自旋
        while (true) {
            Boolean hasLocked = redisTemplate.opsForValue()
                .setIfAbsent(RedisLockConst.LOCK_KEY, lockValue, expireTime, timeUnit);

            // 锁定成功, 退出循环执行外部的业务逻辑
            if (Objects.nonNull(hasLocked) && hasLocked) {

                // 维持锁持续时间, 知直到业务逻辑结束
                keepLockAlived();

                log.info("成功获取锁");
                break;
            }

            try {
                // 100 毫秒获取一次
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        return lockValue;
    }

    /**
     * 进行解锁
     */
    public void unlock() {
        // 如果锁不存在
        if (Objects.isNull(this.lockValue)) {
            throw new RuntimeException("锁不存在");
        }

        Object oldLockValue = redisTemplate.opsForValue().get(RedisLockConst.LOCK_KEY);

        // 锁存在，并且锁是自己的
        if (Objects.nonNull(oldLockValue) && lockValue.equals(oldLockValue.toString())) {
            log.info("解锁成功");

            redisTemplate.delete(RedisLockConst.LOCK_KEY);
        }
    }


    /**
     * 保证在业务结束之前锁一直是存活状态
     */
    @SuppressWarnings("all")
    public void keepLockAlived() {
        threadPoolTaskExecutor.execute(() -> {
            while (true) {
                if (Objects.isNull(lockValue)) {
                    throw new RuntimeException("锁不存在");
                }

                // 锁失效活着不是自己的锁
                Object lockValueObj = redisTemplate.opsForValue().get(RedisLockConst.LOCK_KEY);
                if (Objects.isNull(lockValueObj) || !lockValueObj.toString().equals(lockValue)) {
                    break;
                }

                // 进行时间的更新
                redisTemplate.expire(RedisLockConst.LOCK_KEY, expireTime, timeUnit);

                try {
                    Thread.sleep(timeUnit.toMillis(expireTime) / 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
