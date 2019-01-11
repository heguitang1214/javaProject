package com.zero.demo.dao.jta;

import ai.yunxi.demo.dao.exp.ReduceStockException;

public interface ProductDao {
	public int reduceStock(Integer productId, Integer amount) throws ReduceStockException;
}
