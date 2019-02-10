package com.zero.redis;

import io.lettuce.core.RedisClient;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by heguitang on 2019/2/7.
 * 分布式锁
 */
public class RedisDistributedLock implements Lock {

    //锁信息的上下文，保存当前锁的持有人id
    private ThreadLocal<String> localContext = new ThreadLocal<>();
    //默认锁的超时时间
    private long time = 100;
    //线程重入，表示当前线程是否获取了锁
    private Thread exclusiveOwnerThread;

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    //    阻塞式
    @Override
    public void lock() {
        while (!tryLock()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean tryLock() {
//        try {
            return tryLock(time, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return false;//todo
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        //为每个锁的持有人分配唯一的id
        String id = UUID.randomUUID().toString();
        Thread t = Thread.currentThread();
        Jedis jedis = JedisClient.getClient();

        //加锁并设置锁的有效期
        if ("OK".equals(jedis.set("lock", id, "NX", "PX", unit.toMillis(time)))) {
            localContext.set(id);//记录锁的持有人id
            setExclusiveOwnerThread(t);//记录当前线程
            return true;
        } else if (exclusiveOwnerThread == t) {//当前线程已经获得了锁，可重入
            //可以实现重入计数器
            return false;
        }
        return false;
    }

    @Override
    public void unlock() {
        String script = null;

        try {
            Jedis jedis = JedisClient.getClient();
            script = inputStream2String(getClass().getResourceAsStream("/redis.script"));
            if (localContext.get() == null) {
                return;
            }
            //删除锁
            jedis.eval(script, Arrays.asList("lock"), Arrays.asList(localContext.get()));
            localContext.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    //中断,停止加锁
    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        while (!tryLock()) {
            Thread.sleep(100);
        }
    }

    private String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}
