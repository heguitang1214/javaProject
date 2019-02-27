package ai.yunxi.sharding.service;

import ai.yunxi.sharding.model.Order;
import ai.yunxi.sharding.model.OrderItem;

import java.util.List;

public interface OrderService {

    public List<Order> findHint();

    void save();

    public void save(Order order, OrderItem item);

}
