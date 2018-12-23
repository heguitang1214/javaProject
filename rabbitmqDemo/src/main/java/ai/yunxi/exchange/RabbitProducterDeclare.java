package ai.yunxi.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 
 * @author 小五老师
 * @createTime 2018年9月4日 下午2:44:47
 * 交换器声明
 */
public class RabbitProducterDeclare {

	private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
	private static final String ROUTING_KEY = "demo.routingkey"; //路由键
	private static final String QUEUE_NAME = "demo.exchange.queue"; //队列
	private static final String IP_ADDRESS = "192.168.110.130";
	private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672
	
	public static void durable() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个非持久化交换器，RabbitMQ重启后，交换器会消失:durable=true
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , true , false , false , null);
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		
		String message = "Hello Exchange.";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void autoDelete() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个自动删除交换器:autoDelete=true
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , true , true , false , null);
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		
		String message = "Hello Exchange.";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void internal() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个内置交换器:internal=true
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , true , false , true , null);
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		
		String message = "Hello Exchange.";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void passive() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		
//		channel.exchangeDeclarePassive(EXCHANGE_NAME);
		//创建一个交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , true , false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
//		
//		String message = "Hello Exchange.";
//		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
//				MessageProperties.PERSISTENT_TEXT_PLAIN,
//				message.getBytes());
//		//关闭资源
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
		
//		channel.exchangeDelete(EXCHANGE_NAME); //删除交换器
		channel.exchangeDelete(EXCHANGE_NAME, true); //删除交换器，当交换器没有被使用的时候
		
		//关闭资源
		channel.close();
		connection.close();
	}
	
	public static void main(String[] args) throws Exception {
//		durable(); //创建非持久化交换器
//		autoDelete(); //创建自动删除交换器
//		internal(); //创建内置交换器
//		passive(); //检测交换器是否存在
//		delete(); //删除交换器
	}
}
