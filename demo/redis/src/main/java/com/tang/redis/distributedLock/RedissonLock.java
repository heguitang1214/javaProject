package com.tang.redis.distributedLock;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * RedissonLock实现分布式锁
 */
public class RedissonLock {

    public static void main(String[] args) {
        Config config1 = new Config();
        config1.useSingleServer().setAddress("redis://192.168.0.1:5378")
                .setPassword("123456").setDatabase(0);
        RedissonClient redissonClient1 = Redisson.create(config1);

        Config config2 = new Config();
        config2.useSingleServer().setAddress("redis://192.168.0.1:5379")
                .setPassword("123456").setDatabase(0);
        RedissonClient redissonClient2 = Redisson.create(config2);

        Config config3 = new Config();
        config3.useSingleServer().setAddress("redis://192.168.0.1:5380")
                .setPassword("123456").setDatabase(0);
        RedissonClient redissonClient3 = Redisson.create(config3);

        String resourceName = "REDLOCK";
        RLock lock1 = redissonClient1.getLock(resourceName);
        RLock lock2 = redissonClient2.getLock(resourceName);
        RLock lock3 = redissonClient3.getLock(resourceName);

        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        boolean isLock;
        try {
            isLock = redLock.tryLock(500, 30000, TimeUnit.MILLISECONDS);
            System.out.println("isLock = " + isLock);
            if (isLock) {
                //TODO if get lock success, do something;
                Thread.sleep(30000);
            }
        } catch (Exception e) {
            // 获取锁异常
            e.printStackTrace();
        } finally {
            // 无论如何, 最后都要解锁
            System.out.println("");
            redLock.unlock();
        }
    }

}
