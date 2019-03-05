package ai.yunxi.sharding;

import ai.yunxi.sharding.model.*;
import ai.yunxi.sharding.service.OrderService;
import ai.yunxi.sharding.service.ProvinceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 1.测试广播表:插入到所有的表中
 * 2.测试使用表达式进行数据分片
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class StandardShardingApplicationTests {

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    OrderService orderService;

    /**
     * 测试广播表
     */
    @Test
    public void test() {
        Province pro = new Province();
        pro.setId(200);
        pro.setName("上海");
        provinceService.save(pro);
    }


    /**
     * 测试使用表达式进行数据分片
     */
    @Test
    public void contextLoads() {
        orderService.save();
    }


    /**
     * 测试sharding-jdbc的事务问题。
     */
    @Test
    public void contextLoads1() {
        Order order = OrderGenerator.generate();
        order.setUserId(10000000);
        order.setOrderId(1000000);
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());
        orderService.save(order, orderItem);
    }

}

