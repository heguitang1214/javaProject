package rabbitMQ.rpc4Demo;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ RPC 客户端
 */
public class Rabbit4RPCClient {

    private static final String QUEUE_NAME = "demo.rpcqueue"; //RPC请求队列
    private static final String IP_ADDRESS = "47.93.194.11";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        final Channel channel = connection.createChannel();//创建信道

        //每个客户端自己创建匿名队列，用于响应服务端请求
        String CALLBACK_QUEUE = channel.queueDeclare().getQueue(); //回调队列

        final String corrld = UUID.randomUUID().toString();
        String message = "Hello World !";
        BasicProperties props = new BasicProperties.Builder()
                .correlationId(corrld)
                .replyTo(CALLBACK_QUEUE)
                .build();
        //发送消息：使用默认exchange，允许通过routingKey指定message将被发送给哪个queue
        channel.basicPublish("", QUEUE_NAME, props, message.getBytes());

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
                if (corrld.equals(properties.getCorrelationId())) {
                    System.out.println("client recv message:" + new String(body) + "  corrld:" + corrld);
                } else {
                    System.out.println("不是本次请求的消息");
                }
            }
        };
        //接收消息
        channel.basicConsume(CALLBACK_QUEUE, true, consumer);
        //当客户端断开连接后，默认的队列就会删除，也就是排他队列
        TimeUnit.SECONDS.sleep(30);
        channel.close();
        connection.close();
    }
}
