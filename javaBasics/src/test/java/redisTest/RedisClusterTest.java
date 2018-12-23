package redisTest;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import java.util.HashSet;
import java.util.Set;

public class RedisClusterTest {
    @Test
    public void test() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6379));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6380));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6381));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6382));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6383));
        jedisClusterNodes.add(new HostAndPort("47.93.194.11", 6384));
        JedisCluster cluster = new JedisCluster(jedisClusterNodes);

        //执行JedisCluster对象中的方法，方法和redis一一对应。
        cluster.set("cluster-test", "my jedis cluster test");
        String result = cluster.get("cluster-test");
        System.out.println(result);
        //程序结束时需要关闭JedisCluster对象
        cluster.close();
    }
}
