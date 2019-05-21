package com.tang.sharding;

import com.tang.sharding.model.ItemGenerator;
import com.tang.sharding.model.OrderGenerator;
import com.tang.sharding.service.OrderService;
import com.tang.sharding.model.Order;
import com.tang.sharding.model.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试标准分片
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class RangeShardingApplicationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        Order order = OrderGenerator.generate();
        order.setUserId(10000001);
        order.setOrderId(1000001);
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());
        orderService.save(order, orderItem);
    }
}
