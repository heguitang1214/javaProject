package ai.yunxi.queue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年11月10日 下午5:27:23
 * 队列消息封装
 */
public class RabbitQueueMessage {

	private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
	private static final String EXCHANGE_NAME_BAK = "demo.exchange.bak"; //交换器名称
	private static final String ROUTING_KEY = "demo.routingkey"; //路由键
	private static final String QUEUE_NAME = "demo.queue.queue"; //队列
	private static final String QUEUE_NAME_BAK = "demo.queue.queue.bak"; //队列
	private static final String IP_ADDRESS = "192.168.110.130";
	private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672
	
	public static void mandatory() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , false , false , false , null);
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		
		String message = "Hello Queue.";
		//mandatory = true
		channel.basicPublish(EXCHANGE_NAME, "123", true ,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());
		//添加监听器
		channel.addReturnListener(new ReturnListener() {
			
			public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("exchange:"+exchange);
				System.out.println("routingKey:"+routingKey);
				System.out.println("生产者接收到服务器返回的路由失败消息："+new String(body));
			}
		});
		TimeUnit.SECONDS.sleep(5);
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void alternateExchange() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("alternate-exchange", EXCHANGE_NAME_BAK);
		//创建一个交换器 设置备份交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , false , false , false , args);
		channel.exchangeDeclare(EXCHANGE_NAME_BAK, "fanout" , false , false , false , null);
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		channel.queueDeclare(QUEUE_NAME_BAK, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		channel.queueBind(QUEUE_NAME_BAK , EXCHANGE_NAME_BAK , "");
		
		String message = "Hello Queue.";
		//mandatory = true
		channel.basicPublish(EXCHANGE_NAME, "123", true ,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());
		
		TimeUnit.SECONDS.sleep(5);
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void ttl() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个交换器 设置备份交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , false , false , false , null);
		channel.exchangeDeclare(EXCHANGE_NAME+".dead", "direct" , false , false , false , null);
		
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("x-max-length", 5);
		args.put("x-dead-letter-exchange", EXCHANGE_NAME+".dead");
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , args);
		channel.queueDeclare(QUEUE_NAME+".dead", true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		channel.queueBind(QUEUE_NAME+".dead", EXCHANGE_NAME+".dead", ROUTING_KEY);
		
		String message = "Hello Queue.";
		
		AMQP.BasicProperties.Builder builder1 = new AMQP.BasicProperties.Builder(); 
		builder1.deliveryMode(2); //持久化消息

		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
				builder1.build(),
				(message+"1").getBytes());

		AMQP.BasicProperties.Builder builder2 = new AMQP.BasicProperties.Builder(); 
		builder2.deliveryMode(2); //持久化消息
		builder2.expiration("10000"); //设置消息超时时间 ,单位:ms

		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
				builder2.build(),
				(message+"2").getBytes());
		
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
				builder1.build(),
				(message+"3").getBytes());

		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
				builder1.build(),
				(message+"4").getBytes());

		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
				builder1.build(),
				(message+"5").getBytes());
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void main(String[] args) throws Exception {
//		mandatory();
//		alternateExchange(); //备份交换器
		ttl(); //消息过期时间TTL
	}
}
