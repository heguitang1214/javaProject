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
 * 复合索引分片
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class ComplexShardingApplicationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        Order order = OrderGenerator.generate();
        order.setUserId(10000013);
        order.setOrderId(1000010);
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());
        orderService.save(order, orderItem);
    }
}
