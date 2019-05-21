package com.tang.demo.service.jta;

import com.tang.demo.dao.exp.ReduceStockException;
import com.tang.demo.dao.jta.OrderDao;
import com.tang.demo.dao.jta.ProductDao;
import com.tang.demo.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.UserTransaction;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao stockDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private JtaTransactionManager transactionManager;

	// 注解式事务
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createOrder(Order order) throws ReduceStockException {
		orderDao.saveOrder(order);
		stockDao.reduceStock(order.getProductId(), order.getNumber());
//		int i=1/0;
		log.info("==========> finished");
	}

	// 事物模板方式
	public Integer createOrderByTxTemplate(Order order) {
		return transactionTemplate.execute(new TransactionCallback<Integer>() {
			int ret = -1;

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				orderDao.saveOrder(order);
				try {
					stockDao.reduceStock(order.getProductId(), order.getNumber());
					ret = 1;
				} catch (ReduceStockException e) {
					e.printStackTrace();
				}

				log.info("==========> finished");
				return ret;
			}
		});
	}

	// 编程式事物
	public Integer createOrderByTxManual(Order order) {
		UserTransaction tx = transactionManager.getUserTransaction();
		int ret = -1;

		try {
			tx.begin();
			orderDao.saveOrder(order);
			stockDao.reduceStock(order.getProductId(), order.getNumber());
			tx.commit();
			ret = 1;
		} catch (Exception e) {
			log.error("捕获到异常，进行回滚" + e.getMessage());
			e.printStackTrace();

			try {
				tx.rollback();
			} catch (Exception ex) {
				log.error("回滚失败" + ex.getMessage());
				ex.printStackTrace();
			}
		}

		log.info("==========> finished");
		return ret;
	}

}
