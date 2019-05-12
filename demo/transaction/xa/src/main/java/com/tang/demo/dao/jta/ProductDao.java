package com.tang.demo.dao.jta;


import com.tang.demo.dao.exp.ReduceStockException;

public interface ProductDao {
	public int reduceStock(Integer productId, Integer amount) throws ReduceStockException;
}
