package ai.yunxi.sharding;

import ai.yunxi.sharding.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试使用表达式进行数据分片
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class PreciseShardingApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    public void contextLoads() {
        orderService.save();
    }

}

