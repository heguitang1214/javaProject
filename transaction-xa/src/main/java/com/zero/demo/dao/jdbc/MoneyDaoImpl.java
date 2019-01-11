package com.zero.demo.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MoneyDaoImpl implements MoneyDao {

	@Autowired
	private JdbcTemplate jdbcTemplateOrder;

	// 账户价钱操作
	@Override
	public int increase(int id, int money) {
		System.out.println("============> 调用加钱");
		String sql = "update t_money set money = money + %d where id = %d";
		sql = String.format(sql, money, id);
		return jdbcTemplateOrder.update(sql);
	}

	// 账户扣钱操作
	@Override
	public int decrease(int id, int money) throws Exception {
		System.out.println("============> 调用扣钱");
		String sql = "update t_money set money = money - %d"+
	                 " where id = %d and money - %d >= 0;";
		sql = String.format(sql, money, id, money);
		int rows = jdbcTemplateOrder.update(sql);

		if (rows == 0)
			throw new Exception("扣款失败！");

		return rows;
	}

}
