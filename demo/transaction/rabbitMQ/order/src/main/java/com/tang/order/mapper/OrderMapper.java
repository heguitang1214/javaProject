package com.tang.order.mapper;

import com.tang.entity.Order;
import org.apache.ibatis.annotations.Mapper;


/**
 * 修改订单mapper
 */
@Mapper
public interface OrderMapper {
	int insert(Order order);
	int delete(int orderId);
	int update(int orderId);
}
