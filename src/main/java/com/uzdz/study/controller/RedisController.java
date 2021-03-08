package com.uzdz.study.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@ResponseBody
@RestController
public class RedisController {

    @Autowired
    RedisLockRegistry redisLockRegistry;

    @Autowired(required = false)
    private RedisClient redisClient;

    @SentinelResource("redisResouce")
    @GetMapping("/redisInfo")
    public String getRedisInfo() throws ExecutionException, InterruptedException {

        if (redisClient == null) {
            return "failed start redis client";
        }

        RedisAsyncCommands<String, String> async = redisClient.connect().async();

        RedisFuture<String> info = async.info();

        return info.get();
    }

    @GetMapping("/lock")
    public String tryLock() {
        Lock lock = redisLockRegistry.obtain("uzdz:lock");

        boolean isLocked = false;
        try {
            System.out.println("尝试获取锁");
            isLocked = lock.tryLock();
            System.out.println("锁状态：" + isLocked);
            if (isLocked) {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("ok");
                return "成功";
            } else {
                System.out.println("false");
                return "false 请求过于频繁";
            }

        } catch (CannotAcquireLockException | InterruptedException e) {
            System.out.println("error");
            //操作频繁，稍后再试
            return "error 请求过于频繁";
        } finally {
            if (isLocked) {
                // 先判断是否获取了锁标记。如果获取了锁标记，放锁
                // 如果没有获取锁标记就放锁，会抛出异常
                lock.unlock();
            }
        }
    }
}
