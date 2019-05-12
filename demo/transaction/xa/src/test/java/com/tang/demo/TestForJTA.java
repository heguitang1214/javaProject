package com.tang.demo;

import com.tang.demo.dao.exp.ReduceStockException;
import com.tang.demo.model.Order;
import com.tang.demo.service.jta.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationJTA.xml" })
public class TestForJTA {

	@Autowired private OrderService orderService;

	private final int THREAD_NUM = 11;
	private CountDownLatch cdl = new CountDownLatch(THREAD_NUM);

	// 保存数据的线程
	private class SaveThread extends Thread {
		@Override
		public void run() {
			try {
				System.out.println("=======>" + Thread.currentThread().getName());
				saveToDB();
				cdl.countDown();
				System.out.println("=======>" + Thread.currentThread().getName() + " finished.");
			} catch (ReduceStockException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveToDB() throws ReduceStockException {
		Order order = new Order();
		// 对应db0.t_order 增加记录
		order.setProductId(1005);
		order.setCustomer("Five");
		// 对应db1.t_stock 减少库存
		order.setNumber(1);
		orderService.createOrder(order);
	}

	@Test
	public void test() {
		// 模拟10个用户同时下单
		for (int i = 0; i < THREAD_NUM; i++) {
			new SaveThread().start();
		}

		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
