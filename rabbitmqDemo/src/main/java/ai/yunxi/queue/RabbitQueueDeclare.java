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
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 
 * @author 小五老师
 * @createTime 2018年9月4日 下午2:44:47
 * 队列声明
 */
public class RabbitQueueDeclare {

	private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
	private static final String EXCHANGE_NAME_DEAD = "demo.exchange.dead"; //定义一个交换器用于路由从队列中删除的消息
	private static final String ROUTING_KEY = "demo.routingkey"; //路由键
	private static final String QUEUE_NAME = "demo.queue.queue"; //队列
	private static final String QUEUE_NAME_DEAD = "demo.queue.deadqueue"; //队列 for EXCHANGE_NAME_DEAD
	private static final String IP_ADDRESS = "192.168.110.130";
	private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672
	
	public static void exclusive() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , false , false , false , null);
		//创建排他队列
		channel.queueDeclare(QUEUE_NAME, true, true , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		
		String message = "Hello Queue.";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());
		
		//在同一个连接中进行消费
		Connection connection2 = factory.newConnection();//创建新连接
		final Channel channel2 = connection2.createChannel(); //创建新信道
		Consumer consumer = new DefaultConsumer(channel2){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("recv message:"+ new String(body));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				channel2.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel2.basicConsume(QUEUE_NAME, consumer);
		//等待回调行数执行完毕之后，关闭资源
		TimeUnit.SECONDS.sleep(5);
		
		//关闭资源
		channel2.close();
		channel.close();
		connection.close();
	}

	public static void args() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , false , false , false , null);
//		channel.exchangeDeclare(EXCHANGE_NAME_DEAD, "direct" , false , false , false , null);
		
		Map<String, Object> args = new HashMap<String, Object>(); //一些参数
//		args.put("x-message-ttl", 5000); //定义消息过期时间，单位毫秒ms
//		args.put("x-expires", 10000); //定义队列过期时间，单位毫秒ms，当队列在指定时间内未被使用时，自动删除该队列。
//		args.put("x-max-length", 10); //定义队列最大长度，队列中消息最大个数
//		args.put("x-max-length-bytes", 500); //定义队列最大占用空间大小，单位B，不仅消息还包括标签等
//		args.put("x-dead-letter-exchange", EXCHANGE_NAME_DEAD); //当队列消息长度大于最大长度、或者过期的 等，将从队列中删除的消息推送到指定的交换器中（死信交换器）
//		args.put("x-dead-letter-routing-key", "test");
		
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
//		channel.queueDeclare(QUEUE_NAME_DEAD, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
//		channel.queueBind(QUEUE_NAME_DEAD , EXCHANGE_NAME_DEAD , ROUTING_KEY);
		
		
		for (int i = 0; i < 12; i++) {
			String message = "Hello Queue "+i;
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
					MessageProperties.PERSISTENT_TEXT_PLAIN,
					message.getBytes());
		}
		
		
		//关闭资源
//		channel.close();
//		connection.close();
	}

	public static void passive() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//检查队列是否存在
		channel.queueDeclarePassive(QUEUE_NAME);
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void delete() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		
//		channel.queueDelete(QUEUE_NAME);//删除队列
		channel.queueDelete(QUEUE_NAME, true, false); //当队列为空时删除队列
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void purge() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		
		channel.queuePurge(QUEUE_NAME);//清空队列
		//关闭资源
		channel.close();
		connection.close();
	}
	
	
	public static void main(String[] args) throws Exception {
//		exclusive(); //创建排他队列
//		args();
//		passive(); //检测队列是否存在
		delete(); //删除队列
//		purge(); //清空队列
	}
}
