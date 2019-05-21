package com.tang.demo.service.jta;

import com.tang.demo.dao.exp.ReduceStockException;
import com.tang.demo.model.Order;

public interface OrderService {
	public void createOrder(Order order) throws ReduceStockException;
	
	public Integer createOrderByTxTemplate(Order order);
	
	public Integer createOrderByTxManual(Order order);

}
