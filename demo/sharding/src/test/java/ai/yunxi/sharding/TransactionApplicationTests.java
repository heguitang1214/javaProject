package ai.yunxi.sharding;

import ai.yunxi.sharding.model.ItemGenerator;
import ai.yunxi.sharding.model.Order;
import ai.yunxi.sharding.model.OrderGenerator;
import ai.yunxi.sharding.model.OrderItem;
import ai.yunxi.sharding.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * sharding-jdbc事务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class TransactionApplicationTests {

    @Autowired
    OrderService orderService;

    /**
     * 测试sharding-jdbc的事务问题。
     */
    @Test
    public void contextLoads() {
        Order order = OrderGenerator.generate();
        order.setUserId(10000000);
        order.setOrderId(1000000);
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());
        orderService.save(order, orderItem);
    }

}
