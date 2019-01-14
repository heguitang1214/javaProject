package com.zero.demo.dao.jta;


import com.zero.demo.dao.exp.ReduceStockException;

public interface ProductDao {
	public int reduceStock(Integer productId, Integer amount) throws ReduceStockException;
}
