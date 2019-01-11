package com.zero.demo.dao.jta;

import com.zero.demo.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ai.yunxi.demo.model.Order;

@Repository
public class OrderDaoImpl implements OrderDao {
	private static final Logger log = LoggerFactory.getLogger(OrderDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplateOrder;

	// 保存订单操作
	@Override
	public void saveOrder(Order order) {
		String sql = "insert into t_order(product_id,customer,number) values(%d, '%s', %d)";
		sql = String.format(sql, order.getProductId(), order.getCustomer(), order.getNumber());
		log.info("==========> " + sql.toString());
		jdbcTemplateOrder.execute(sql);
	}
}
