package ai.yunxi.exchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 
 * @author 小五老师
 * @createTime 2018年9月4日 下午2:44:47
 * Topic类型交换器：同direct，但是这里的匹配规则有些不同，允许使用表达式模糊匹配
 */
public class RabbitProducterByTopic {

	private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
	private static final String ROUTING_KEY1 = "*.error"; //路由键1 error
	private static final String ROUTING_KEY2 = "*.info"; //路由键2 info
	private static final String QUEUE_NAME1 = "demo.exchange.queue1"; //队列1   error
	private static final String QUEUE_NAME2 = "demo.exchange.queue2"; //队列2   info
	private static final String IP_ADDRESS = "192.168.110.130";
	private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个 type="topic" 的交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "topic" , true , false , null);
		//创建两个队列
		channel.queueDeclare(QUEUE_NAME1, true, false , false , null);
		channel.queueDeclare(QUEUE_NAME2, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME1 , EXCHANGE_NAME , ROUTING_KEY1);
		channel.queueBind(QUEUE_NAME2 , EXCHANGE_NAME , ROUTING_KEY2);
		for (int i = 0; i < 10; i++) {
			String message = "Number:"+i;
			channel.basicPublish(EXCHANGE_NAME, i%2==0?"log.error":"log.info",
					MessageProperties.PERSISTENT_TEXT_PLAIN,
					message.getBytes());
		}
		//关闭资源
		channel.close();
		connection.close();
	}
}
