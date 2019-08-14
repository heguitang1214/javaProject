package com.tang.redis.distributedLock;

import com.tang.redis.JedisClient;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by heguitang on 2019/2/7.
 * 分布式锁
 * @author Tang
 */
public class RedisDistributedLock implements Lock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_KEY = "lock";

    /**
     * 锁信息的上下文，保存当前锁的持有人id
     * 自己记录线程的唯一标识，不需要当作参数传递进来
     */
    private ThreadLocal<String> localContext = new ThreadLocal<>();

    /**
     * 默认锁的超时时间
     */
    private long time = 100;

    /**
     * 线程重入，表示当前线程是否获取了锁
     */
    private Thread exclusiveOwnerThread;

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    /**
     * 阻塞式
     */
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
//        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        //为每个锁的持有人分配唯一的id
        String id = UUID.randomUUID().toString();
        Thread t = Thread.currentThread();
        Jedis jedis = JedisClient.getClient();

        //加锁并设置锁的有效期
        if (LOCK_SUCCESS.equals(jedis.set(LOCK_KEY, id, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, unit.toMillis(time)))) {
            //记录锁的持有人id
            localContext.set(id);
            //记录当前线程
            setExclusiveOwnerThread(t);
            return true;
            //当前线程已经获得了锁，可重入
        } else if (exclusiveOwnerThread == t) {
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
//            jedis.eval(script, Arrays.asList(LOCK_KEY), Arrays.asList(localContext.get()));
            jedis.eval(script, Collections.singletonList(LOCK_KEY), Collections.singletonList(localContext.get()));
            localContext.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * 中断,停止加锁
     * @throws InterruptedException 异常
     */
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
