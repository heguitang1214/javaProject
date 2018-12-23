package ai.yunxi.exchange;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 * @author 小五老师
 * @createTime 2018年9月4日 下午2:44:30
 * 
 */
public class RabbitConsumer4Exchange {
	private static final String QUEUE_NAME = "demo.exchange.queue"; //队列
	private static final String IP_ADDRESS="192.168.110.130";
	private static final int PORT=5672;
	
	public static void main(String[] args) throws Exception {
		Address[] addresses = new Address[]{new Address(IP_ADDRESS, PORT)};
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		
		//这里的连接方式与生产者的略有不同，注意辨别区别
		Connection connection = factory.newConnection(addresses); //创建连接
		final Channel channel = connection.createChannel(); //创建信道
		Consumer consumer = new DefaultConsumer(channel){

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("recv message:"+ new String(body));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, consumer);
		//等待回调行数执行完毕之后，关闭资源
		TimeUnit.SECONDS.sleep(12);
		channel.close();
		connection.close();
				
	}
}
