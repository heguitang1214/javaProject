package rabbitMQ.redisDelayDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbitMQ.utils.CalendarUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.Set;


/**
 * Jedis工具
 */
@Component
public class RedisUtils {

    @Autowired
    private JedisPool jedisPool;

    //新增订单
    public void addItem4Zset(String key, double score, String member) {
        Jedis jedis = jedisPool.getResource();
        //向redis的zset中添加数据
        jedis.zadd(key, score, member);
        System.out.println(CalendarUtils.getCurrentTimeByStr(0) + " 添加了一个订单[key:" + key + ",score:" + score + ",orderId:" + member + "]");
    }

    //扫描redis zset集合， 判断是不是有超时的订单需要处理
    public void doFind4Zset(String key) {
        Jedis jedis = jedisPool.getResource();
        //消费者循环获取数据
        while (true) {
            //0,0:获取第一个元素
            //0,-1:获取整个zset集合
            //0,2:或者zset中第0,1,2三个元素
            Set<Tuple> zrangeWithScores = jedis.zrangeWithScores(key, 0, 0); //获取集合中第一个元素

            if (zrangeWithScores != null && !zrangeWithScores.isEmpty()) {
                //拿到了元素
                //通过它的score值（元素的超时时间戳） 与当前时间戳进行对比， 判断是否超时
                Tuple tuple = (Tuple) zrangeWithScores.toArray()[0];
                double score = tuple.getScore(); //得到了元素的超时时间戳
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis >= score) {
                    //订单超时了
                    String orderId = tuple.getElement();//member
                    Long row = jedis.zrem(key, orderId);  //从集合中删除这个元素
                    if (row != null && row > 0) {
                        System.out.println(CalendarUtils.getCurrentTimeByStr(0) + " 消费了一个超时的任务[key:" + key + ",score:" + score + ",orderId:" + orderId + "]");
                    }
                }
            } else {
                //没有拿到元素，集合中没有需要处理的任务，休眠1/10s
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
