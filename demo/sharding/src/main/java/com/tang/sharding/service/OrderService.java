package com.tang.sharding.service;

import com.tang.sharding.model.Order;
import com.tang.sharding.model.OrderItem;

import java.util.List;

public interface OrderService {

    List<Order> findHint();

    void save();

    void save(Order order, OrderItem item);

}
