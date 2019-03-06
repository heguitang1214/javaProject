package ai.yunxi.sharding;

import ai.yunxi.sharding.service.OrderService;
import io.shardingsphere.api.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 强制路由分片策略
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class HintApplicationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        HintManager hintManager = HintManager.getInstance();
        //指定数据库
        hintManager.addDatabaseShardingValue("t_order", 1);
        //指定表
        hintManager.addTableShardingValue("t_order", 1);
//        hintManager.setDatabaseShardingValue(1);
        System.out.println(orderService.findHint());
    }
}
