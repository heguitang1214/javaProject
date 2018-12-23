package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;

/**
 * Redis管道
 **/
public class RedisPipeline {

    /**
     * 非事务类型的管道操作
     * @param userId 用户ID
     * @param shops 其他
     */
    public void follow(long userId, String... shops) {
        Jedis client = new Jedis("47.93.194.11", 6379);
        client.auth("heguitang");
        client.flushDB();

        //添加数据
        Pipeline pipeline = client.pipelined();

        for (int i = 0; i < shops.length; i++ ){
//            pipeline.sadd("key" + userId, shop);
            String shop = shops[i];
            pipeline.set("key" + i, userId + shop);
        }

        //异步获取返回结果
        List<Object> results = pipeline.syncAndReturnAll();

        for (Object result : results) {
            System.out.println("获取Pipeline异步返回的结果：" + result);
        }
    }

    /**
     * 事务类型的管道操作
     * @param userId 用户ID
     * @param shop 其他
     */
    public void follow(long userId, String shop) {
        Jedis client = new Jedis("47.93.194.11", 6379);
        client.auth("heguitang");
        client.flushDB();

        //添加数据
        Pipeline pipeline = client.pipelined();
        //事务操作
        pipeline.multi();
        pipeline.sadd("follow:shop:" + userId, shop);
        pipeline.sadd("followed:" + shop, String.valueOf(userId));
        //执行
        Response<List<Object>> response = pipeline.exec();

        //异步获取返回结果
        List<Object> results = pipeline.syncAndReturnAll();

        for (Object o : response.get()) {
            System.out.println("事务执行:" + o);
        }
        for (Object result : results) {
            System.out.println("获取Pipeline异步返回的结果：" + result);
        }
    }


    public static void main(String[] args) {
        /*
            pipeline是否是事务，结果会不一样
         */
        RedisPipeline pipeline = new RedisPipeline();
        pipeline.follow(1, "美邦正品折扣店", "天梭腕表京东自营专区", "Aptamil爱他美官方海外旗舰店");
//        pipeline.follow(1, "philips");
    }
}
