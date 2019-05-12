package com.tang.demo.dao.jta;

import com.tang.demo.model.Order;

public interface OrderDao {
	public void saveOrder(Order order);
}
