package com.zero.demo.service.jta;

import com.zero.demo.dao.exp.ReduceStockException;
import com.zero.demo.model.Order;

public interface OrderService {
	public void createOrder(Order order) throws ReduceStockException;
	
	public Integer createOrderByTxTemplate(Order order);
	
	public Integer createOrderByTxManual(Order order);

}
