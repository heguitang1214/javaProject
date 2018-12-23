package ai.yunxi.demo.service.jta;

import ai.yunxi.demo.dao.exp.ReduceStockException;
import ai.yunxi.demo.model.Order;

public interface OrderService {
	public void createOrder(Order order) throws ReduceStockException;
	
	public Integer createOrderByTxTemplate(Order order);
	
	public Integer createOrderByTxManual(Order order);

}
