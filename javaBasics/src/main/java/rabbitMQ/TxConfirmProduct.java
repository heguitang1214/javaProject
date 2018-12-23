package rabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 	RabbitMQ事务
 */
public class TxConfirmProduct {

	private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
	private static final String ROUTING_KEY = "demo.routingkey"; //路由键
	private static final String QUEUE_NAME = "demo.queue"; //队列名称
	private static final String IP_ADDRESS = "47.93.194.11";
	private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory() ;
		factory.setHost(IP_ADDRESS) ;
		factory.setPort(PORT) ;
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Connection connection = factory.newConnection();//创建连接
		Channel channel = connection.createChannel();//创建信道
		//创建一个 type="direct" 、持久化的、非自动删除的交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct" , true , false , null);
		//创建一个持久化、非排他的、非自动删除的队列
		channel.queueDeclare(QUEUE_NAME, true, false , false , null);
		//将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME , EXCHANGE_NAME , ROUTING_KEY);
		//发送一条持久化的消息: hello world !
		String message = "Hello World !";
		
		//开启事务机制
		channel.txSelect();

		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY ,
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());

		//提交事务
		channel.txCommit();
		//回滚事务
//		channel.txRollback();
		TimeUnit.SECONDS.sleep(1);
		//关闭资源
		channel.close() ;
		connection.close();
	}
}
