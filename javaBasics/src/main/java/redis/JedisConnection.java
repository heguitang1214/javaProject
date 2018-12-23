package redis;

import redis.clients.jedis.Jedis;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/24]
 * Jeids客户端连接Redis服务器
 */
public class JedisConnection {

    public static void main(String[] args) {
        getRedisConnection();
//        connectionRedis();
    }

    private static void connectionRedis(){
        // 连接 Redis 服务
        Jedis jedis = new Jedis("47.93.194.11", 6379); // 默认端口
        //Jedis jedis = new Jedis("10.80.248.22",6379); // 指定端口
        jedis.auth(""); // 指定密码
        System.out.println("Connection to server sucessfully......");
        //查看服务是否运行
        System.out.println("Server is running: " + jedis.ping());
        // 设置 redis 字符串数据
        jedis.set("redis", "Redis 1");
        // 获取存储的数据并输出
        System.out.println("Stored string in redis:: " + jedis.get("redis"));
        System.out.println("redis : " + jedis.get("redis"));
    }


    static Jedis getRedisConnection(){
        // 连接 Redis 服务
        Jedis jedis = new Jedis("47.93.194.11", 6379); // 默认端口
        jedis.auth(""); // 指定密码
        System.out.println("Connection to server sucessfully......");
        //查看服务是否运行
        System.out.println("Server is running: " + jedis.ping());
        // 获取存储的数据并输出
        return jedis;
    }

}
