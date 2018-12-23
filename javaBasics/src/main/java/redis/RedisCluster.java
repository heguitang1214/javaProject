package redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis线程池
 **/
public class RedisCluster {

//    private final JedisCluster jedisCluster;
    private RedisCluster() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //线程池的最大连接数，如果池中的连接数为0，那么客户端需要等待
        poolConfig.setMaxTotal(100);
        //请求量过大导致调用者所在线程阻塞,可以通过设置blockWhenExhausted=true
        // 并且设置maxWaitMillis指定最大等待时间,超过该值后将解除阻塞。
        //客户端虎丘返回值：Could not get a resource from the pool
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(TimeUnit.SECONDS.toMillis(2));

        //每次从连接池取连接时会做连接有效性的测试(ping) ,无效连接会被移除，每次取连接时多执行一次ping命令。
        poolConfig.setTestOnBorrow(true);

        //定期清理失效连接
        //表示后台线程每2s(TimeBetweenEvictionRunsMillis)会执行一次清理任务,将空闲时间>1s(MinEvictableIdleTimeMillis)的连接移除
        poolConfig.setTimeBetweenEvictionRunsMillis(TimeUnit.SECONDS.toMillis(2));
        poolConfig.setMinEvictableIdleTimeMillis(TimeUnit.SECONDS.toMillis(1));

        //Jedis集群
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6379));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6380));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6381));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6382));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6383));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6384));
        // 根据节点集创集群链接对象
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
        // 节点，超时时间，最多重定向次数，链接池
        jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 100, poolConfig);
//        jedisCluster = new JedisCluster(jedisClusterNodes, 1000, 2000, 100, "heguitang", poolConfig);

        try {
            int num = 10;
            String key = "wusc";
            String value = "";
            for (int i = 1; i <= num; i++) {
                // 存数据
                jedisCluster.set(key + i, "WuShuicheng" + i);
                // 取数据
                value = jedisCluster.get(key + i);
                System.out.println(key + i + "=" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisCluster != null) {
                try {
                    jedisCluster.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


//    private void queryProducts() {
////        Jedis client = null;
//        try {
////            client = jedisPool.getResource();
//            Set<Tuple> products = jedisCluster.zrangeWithScores("products", 0, -1);
//            for (Tuple product : products) {
//                System.out.println("商品名称:" + product.getElement() + ",价格:" + product.getScore());
//            }
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        } finally {
//            //每次连接使用结束后，需要释放连接，否则，连接被占用，会一直阻塞其他的客户端。
////            if (client != null) {
////                client.close();
////            }
//        }
//    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        RedisCluster redisCluster = new RedisCluster();
        System.out.println("123456");

//        for (int i = 0; i < 15; i++) {
//            new Thread(new Runnable() {
//                public void run() {
//                    long begin = System.currentTimeMillis();
//                    redisCluster.queryProducts();
//                    System.out.println(Thread.currentThread().getName() + "执行时长:" + (System.currentTimeMillis() - begin));
//                }
//            }, "c-" + i).start();
//        }
//
//
//        Thread.sleep(TimeUnit.SECONDS.toMillis(8));
//        System.out.println("=========================================分割线=====================================================");
//
//        for (int i = 0; i < 15; i++) {
//            new Thread(new Runnable() {
//                public void run() {
//                    long begin = System.currentTimeMillis();
//                    redisCluster.queryProducts();
//                    System.out.println(Thread.currentThread().getName() + "执行时长:" + (System.currentTimeMillis() - begin));
//                }
//            }, "c-" + i).start();
//        }
    }

}
