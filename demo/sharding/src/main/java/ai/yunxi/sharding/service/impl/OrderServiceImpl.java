package ai.yunxi.sharding.service.impl;

import ai.yunxi.sharding.mapper.OrderItemMapper;
import ai.yunxi.sharding.mapper.OrderMapper;
import ai.yunxi.sharding.model.ItemGenerator;
import ai.yunxi.sharding.model.Order;
import ai.yunxi.sharding.model.OrderGenerator;
import ai.yunxi.sharding.model.OrderItem;
import ai.yunxi.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public void save(Order order, OrderItem item) {
        orderMapper.save(order);
        orderItemMapper.save(item);
    }

    @Override
    public List<Order> findHint() {
        return orderMapper.selectHint();
    }
}
