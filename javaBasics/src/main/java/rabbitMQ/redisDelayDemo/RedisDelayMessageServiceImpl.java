package rabbitMQ.redisDelayDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rabbitMQ.utils.CalendarUtils;
import rabbitMQ.utils.DelayMessageService;


/**
 * Redis延时队列的实现
 */
@Service("redisDelayMessageServiceImpl")
public class RedisDelayMessageServiceImpl implements DelayMessageService {

    @Autowired
    private RedisUtils redis;

    private String key = "DELAY.ORDERID";

    //生产者   向集合中添加元素
    public void product() {
        String orderId = "1010101";
        for (int i = 0; i < 10; i++) {
            //创建订单
            redis.addItem4Zset(key, CalendarUtils.getCurrentTimeInMillis(10), orderId + i);
            if (i % 3 == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //消费者   扫描最小元素，判断是否超时
    public void consumer() {
        redis.doFind4Zset(key);
    }
}
