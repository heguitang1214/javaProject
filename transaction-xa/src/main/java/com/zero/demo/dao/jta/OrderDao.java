package com.zero.demo.dao.jta;

import ai.yunxi.demo.model.Order;
import com.zero.demo.model.Order;

public interface OrderDao {
	public void saveOrder(Order order);
}
