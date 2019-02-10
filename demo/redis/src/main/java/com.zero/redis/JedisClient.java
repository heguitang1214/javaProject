package com.zero.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by heguitang on 2019/2/7.
 */
public class JedisClient {
    private static final JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000 * 10);
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "localhost");
    }

    public static Jedis getClient() {
        return pool.getResource();
    }

}
