package com.zero.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zero.core.entity.Order;

/**
 * 修改订单mapper
 */
@Mapper
public interface OrderMapper {
	int insert(Order order);
	int delete(int orderId);
	int update(int orderId);
}
