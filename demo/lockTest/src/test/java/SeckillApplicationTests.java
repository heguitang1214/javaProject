//import org.apache.activemq.command.ActiveMQQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import javax.jms.Queue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

	RestTemplate restTemplate = new RestTemplate();
	//测试nginx的正常请求和限流请求
	String url_nginx= "http://127.0.0.1";
    //测试数据库-无锁
	String url_nolock= "http://127.0.0.1:8080/nolock";
//	//测试乐观锁
	String url_optimistic= "http://127.0.0.1:8080/optimistic";
//	//测试带重试的乐观锁
	String url_optimisticWithRetry= "http://127.0.0.1:8080/optimisticWithRetry";
//	//测试悲观锁
	String url_pessimistic= "http://127.0.0.1:8080/pessimistic";
//	//测试redis
	String url_byredis= "http://127.0.0.1:8080/byRedis";
//
//	//guava 限流
//	//String url_tryRateLimit="http://127.0.0.1:8080/tryRateLimit";

	//测试nginx 使用20个并发，测试购买商品使用500个并发买300个商品
	private static final int amount=500;
	//发令枪，目的是模拟真正的并发，等所有线程都准备好一起请求。
	private CountDownLatch countDownLatch = new CountDownLatch(amount);

	@Test
	public void contextLoads() throws Exception{
		System.out.println("开始卖："+System.currentTimeMillis());
		for(int i=0; i<amount; i++){
			new Thread(new Request()).start();
			countDownLatch.countDown();
		}
		Thread.currentThread().sleep(100000);

	}

	public class Request implements Runnable{
		@Override
		public void run() {
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//System.out.println(restTemplate.getForObject(url_nginx, String.class));
			restTemplate.getForObject(url_pessimistic, String.class);

		}
	}


}
