package redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis线程池
 **/
public class RedisPool {

    private final JedisPool jedisPool;

    private RedisPool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //线程池的最大连接数，如果池中的连接数为0，那么客户端需要等待
        poolConfig.setMaxTotal(1);
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

        jedisPool = new JedisPool(poolConfig, "47.93.194.11", 6379, 10000,"heguitang");
        Jedis client = null;
        try {
            client = jedisPool.getResource();
            //清空数据库
            client.flushDB();
            Map<String, Double> products = new HashMap<>();
            products.put("iPhoneX", 6888.00);
            products.put("HUAWEI Mate 20", 4499.00);
            products.put("GalaxyS8+", 4269.00);
            products.put("VIVO x21", 2798.00);
            client.zadd("products", products);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    private void queryProducts() {
        Jedis client = null;
        try {
            client = jedisPool.getResource();
            Set<Tuple> products = client.zrangeWithScores("products", 0, -1);
            for (Tuple product : products) {
                System.out.println("商品名称:" + product.getElement() + ",价格:" + product.getScore());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            //每次连接使用结束后，需要释放连接，否则，连接被占用，会一直阻塞其他的客户端。
            if (client != null){
                client.close();
            }
        }
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        final RedisPool redisPool = new RedisPool();

        for (int i = 0; i < 15; i++) {
            new Thread(new Runnable() {
                public void run() {
                    long begin = System.currentTimeMillis();
                    redisPool.queryProducts();
                    System.out.println(Thread.currentThread().getName() + "执行时长:" + (System.currentTimeMillis() - begin));
                }
            }, "c-" + i).start();
        }


        Thread.sleep(TimeUnit.SECONDS.toMillis(8));
        System.out.println("=========================================分割线=====================================================");

        for (int i = 0; i < 15; i++) {
            new Thread(new Runnable() {
                public void run() {
                    long begin = System.currentTimeMillis();
                    redisPool.queryProducts();
                    System.out.println(Thread.currentThread().getName() + "执行时长:" + (System.currentTimeMillis() - begin));
                }
            }, "c-" + i).start();
        }
    }

}
