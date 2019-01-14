package com.zero;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.zero.utils.MQProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TicketClientApplication.class)
public class TicketMQClientTest {


	@Autowired
	private AmqpTemplate amqpTemplate;
	
	private final int THREAD_NUM=1000;
	private final CountDownLatch cdl = new CountDownLatch(THREAD_NUM);
	
	public void test0(){
		amqpTemplate.convertAndSend(MQProperties.EXCHANGE_NAME, MQProperties.ROUTE_KEY, "100101");
	}
	
	@Test
	public void test1() {
		for (int i = 0; i < THREAD_NUM; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						cdl.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					amqpTemplate.convertAndSend(MQProperties.EXCHANGE_NAME, MQProperties.ROUTE_KEY, "100101");
				}
			}).start();
			cdl.countDown();
		}
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
