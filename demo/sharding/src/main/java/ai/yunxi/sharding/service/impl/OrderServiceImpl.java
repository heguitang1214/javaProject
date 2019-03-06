package ai.yunxi.sharding.service.impl;

import ai.yunxi.sharding.mapper.OrderItemMapper;
import ai.yunxi.sharding.mapper.OrderMapper;
import ai.yunxi.sharding.model.ItemGenerator;
import ai.yunxi.sharding.model.Order;
import ai.yunxi.sharding.model.OrderGenerator;
import ai.yunxi.sharding.model.OrderItem;
import ai.yunxi.sharding.service.OrderService;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单的实现类
 * @author tang
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@ShardingTransactionType(TransactionType.LOCAL)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public void save() {
        Order order = OrderGenerator.generate();
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());

        orderMapper.save(order);
        orderItemMapper.save(orderItem);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.LOCAL)
    public void save(Order order, OrderItem item) {
        System.out.println("haha 我执行了");
        orderMapper.save(order);
        int i = 90 / 0;
        System.out.println(i);
        orderItemMapper.save(item);
    }

    @Override
    public List<Order> findHint() {
        return orderMapper.selectHint();
    }


}
