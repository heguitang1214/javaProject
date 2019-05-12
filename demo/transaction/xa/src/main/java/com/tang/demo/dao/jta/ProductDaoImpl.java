package com.tang.demo.dao.jta;

import com.tang.demo.dao.exp.ReduceStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ProductDaoImpl implements ProductDao {
	private static final Logger log = LoggerFactory.getLogger(ProductDao.class);

	@Autowired private JdbcTemplate jdbcTemplateStock;

	// 减少库存操作
	@Override
	public int reduceStock(Integer productId, Integer count) throws ReduceStockException {
		StringBuffer sql = new StringBuffer();
		sql.append("update t_product set count = count - ?");
		sql.append(" where product_id = ? and count - ? >= 0");
		log.info("==========> " + sql.toString());
		
		int rows = jdbcTemplateStock.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstm = con.prepareStatement(sql.toString());
				pstm.setInt(1, count);
				pstm.setInt(2, productId);
				pstm.setInt(3, count);
				return pstm;
			}
		});

		if (rows == 0)
			throw new ReduceStockException("库存不够！");
		return rows;
	}
}
