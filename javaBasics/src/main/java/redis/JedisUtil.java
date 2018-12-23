package redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * Created by he_guitang
 * 2018/8/15
 *  Jedis获取和释放分布式锁
 */
public class JedisUtil {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     *      判断该key是否存在,如果存在就返回false,表示获取不到,不存在表示可以获取到该锁
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        /*
          String set(String key, String value, String nxxx, String expx, long time);
          该方法是： 存储数据到缓存中，并制定过期时间和当Key存在时是否覆盖。

          nxxx： 只能取NX或者XX，如果取NX，则只有当key不存在是才进行set，如果取XX，则只有当key已经存在时才进行set
          expx： 只能取EX或者PX，代表数据过期时间的单位，EX代表秒，PX代表毫秒。
          time： 过期时间，单位是expx所代表的单位。
         */
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);

    }


    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     *      释放存在的锁
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        /*
        首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）。
        使用Lua语言来实现是因为要确保上述操作是原子性的。
        在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令.
         */
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        return RELEASE_SUCCESS.equals(result);

    }


}
