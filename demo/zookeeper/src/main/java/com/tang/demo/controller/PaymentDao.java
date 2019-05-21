package com.tang.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Payment findById() {
		String sql = "select * from payment where order_id = 1000000";
		return jdbcTemplate.queryForObject(sql, new RowMapper<Payment>() {
			@Override
			public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
				Payment payment = new Payment();
				payment.setOrderId(rs.getInt("order_id"));
				payment.setBrandId(rs.getInt("brand_id"));
				payment.setProductId(rs.getInt("product_id"));
				payment.setOrderDate(rs.getString("order_date"));
				payment.setPayDate(rs.getString("pay_date"));
				payment.setTotalPrice(rs.getDouble("total_price"));
				payment.setDiscount(rs.getInt("discount"));
				payment.setPayPrice(rs.getDouble("pay_price"));
				return payment;
			}
		});
	}
}
