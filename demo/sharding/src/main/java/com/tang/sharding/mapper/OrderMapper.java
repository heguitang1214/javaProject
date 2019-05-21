package com.tang.sharding.mapper;

import com.tang.sharding.model.Order;

import java.util.List;

public interface OrderMapper {
    public int save(Order order);

    public List<Order> selectHint();
}
