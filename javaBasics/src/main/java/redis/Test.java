package redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/16]
 */
public class Test {

    public static void main(String[] args) {
        concurrentLock();
//        Jedis jedis = JedisConnection.getRedisConnection();
//        System.out.println("redis : " + jedis.get("redis"));
//
//        Boolean b = JedisUtil.tryGetDistributedLock(jedis, "redis1", "1024", 100);
//        System.out.println("获取分布式锁" + b);
//
////        Boolean bb1 = JedisUtil.releaseDistributedLock(jedis, "redis1", "1024");
////        System.out.println("释放分布式锁:" + bb1);
//
//        System.out.println(jedis.exists("redis1"));
//
//        Boolean bb = JedisUtil.releaseDistributedLock(jedis, "redis1", "1024");
//        System.out.println("释放分布式锁:"+ bb);

    }


    /**
     * 测试redis并发获取锁
     */
    private static void concurrentLock(){
        Jedis jedis = JedisConnection.getRedisConnection();
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);//设置初始值为1
        final CountDownLatch cdAnswer = new CountDownLatch(30);//设置初始值为3
        for (int i = 0; i < 30; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        //等待当前的计数器为0
                        cdOrder.await();
                        Jedis jedis = JedisConnection.getRedisConnection();
                        Boolean bb1 = JedisUtil.releaseDistributedLock(jedis, "redis1", "1024");
                        System.out.println("释放分布式锁:"+ bb1);
                        Boolean bb2 = JedisUtil.releaseDistributedLock(jedis, "redis2", "1024");
                        System.out.println("释放分布式锁:"+ bb2);
                        //将cdAnswer计数器上的数值减少1
                        cdAnswer.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        //主线程
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "即将发布命令");
            Boolean b = JedisUtil.tryGetDistributedLock(jedis, "redis1", "1024", 100);
            System.out.println("获取分布式锁" + b);
            Boolean b1 = JedisUtil.tryGetDistributedLock(jedis, "redis2", "1024", 100);
            System.out.println("获取分布式锁" + b1);
            //将cdOrder计数器上的数值减少1
            cdOrder.countDown();
            System.out.println("线程" + Thread.currentThread().getName() + "已发送命令，正在等待结果");
            //等待cdAnswer的计数器为0
            cdAnswer.await();
            System.out.println("线程" + Thread.currentThread().getName() + "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }


}
