package com.tang.order.service;


import com.tang.entity.Order;

/**
 * 订单服务接口
 */
public interface IOrderService {

	int shopping(Order order) throws Exception;

	int shopping4MQ(Order order) throws Exception;
	
	void shoppingRollback(int orderId) throws Exception;
	
	void shoppingCommit(int orderId) throws Exception;
}
