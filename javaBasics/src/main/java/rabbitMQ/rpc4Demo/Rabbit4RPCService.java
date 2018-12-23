package rabbitMQ.rpc4Demo;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * RabbitMQ RPC 服务端
 */
public class Rabbit4RPCService {

    private static final String QUEUE_NAME = "demo.rpcqueue"; //RPC请求队列
    private static final String IP_ADDRESS = "47.93.194.11";
    private static final int PORT = 5672;

    public static void main(String[] args) throws Exception {
        Address[] addresses = new Address[]{new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        //这里的连接方式与生产者的略有不同，注意辨别区别
        Connection connection = factory.newConnection(addresses); //创建连接
        final Channel channel = connection.createChannel(); //创建信道

        //创建请求处理队列，用于服务端接收客户端RPC请求
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("等待RPC请求...");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
                BasicProperties props = new BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                String message = "";
                try {
                    message = new String(body);
                    System.out.println("service recv message:" + message + "   corrld:" + properties.getCorrelationId());
                    message = message + "--- is done.";
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    //服务端处理完消息，发送到回调队列
                    //使用默认exchange，允许通过routingKey指定message将被发送给哪个queue
                    channel.basicPublish("", properties.getReplyTo(), props, message.getBytes("UTF-8"));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        //服务的接收消息
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
