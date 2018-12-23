package ai.yunxi.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2018年11月25日 下午3:31:35
 * 
 */
public class RabbitMQConsumer {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("rabbitstudy");
		factory.setPassword("123456");
		Address[] addresses = new Address[]{new Address("192.168.110.130", 5672)};
		Connection connection = factory.newConnection(addresses); //创建连接
		final Channel channel = connection.createChannel(); //创建信道
		//准备开始接收RabbitMQ数据
		channel.basicConsume("demo.queue", new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("Consumer receive message :" + new String(body)+"; envelope.getDeliveryTag():"+envelope.getDeliveryTag());
				//消费者确认消息接收成功
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		});
		TimeUnit.SECONDS.sleep(1);
		//关闭资源
		channel.close();
		connection.close();
	}
}
