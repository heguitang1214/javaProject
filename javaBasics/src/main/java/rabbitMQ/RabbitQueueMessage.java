package rabbitMQ;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * rabbitmq消息队列的封装
 */
public class RabbitQueueMessage {

    public static void main(String[] args) throws Exception {
//		mandatory();//mandatory = true，消息发送失败，返回给生产者
//		alternateExchange(); //备份交换器
//		queueTTL(); //队列消息过期时间TTL，队列中的消息过期时间一样
//		newsTTL();//消息过期时间TTL
        priorityQueue();//优先级队列
    }

    private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
    private static final String EXCHANGE_NAME_BAK = "demo.exchange.bak"; //交换器名称
    private static final String ROUTING_KEY = "demo.routingkey"; //路由键
    private static final String QUEUE_NAME = "demo.queue.queue"; //队列
    private static final String QUEUE_NAME_BAK = "demo.queue.queue.bak"; //备份队列名
    private static final String IP_ADDRESS = "47.93.194.11";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672

    public static void mandatory() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, false, null);
        //创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        String message = "Hello Queue.";
        //mandatory = true
        channel.basicPublish(EXCHANGE_NAME, "123", true,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        //添加监听器
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
                                     BasicProperties properties, byte[] body) throws IOException {
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
                System.out.println("生产者接收到服务器返回的路由失败消息：" + new String(body));
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
        Map<String, Object> args = new HashMap<>();
        //指定备份交换器
        args.put("alternate-exchange", EXCHANGE_NAME_BAK);
        //创建一个交换器 设置备份交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, false, args);
        channel.exchangeDeclare(EXCHANGE_NAME_BAK, "fanout", false, false, false, null);
        //创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(QUEUE_NAME_BAK, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        channel.queueBind(QUEUE_NAME_BAK, EXCHANGE_NAME_BAK, "");

        String message = "Hello Queue...";
        //发送消息，设置mandatory = true,当ROUTING_KEY路由不到对应的队列的时候，那么该消息就发送到备份队列中
        channel.basicPublish(EXCHANGE_NAME, "12345", true,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
//		TimeUnit.SECONDS.sleep(5);
        //关闭资源
        channel.close();
        connection.close();
    }

    public static void queueTTL() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, false, null);
        //创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "Hello Queue.";

        //设置消息的属性
/*		Map<String, Object> headers = new HashMap<>();
		headers.put("my1", "1111");
		headers.put("my2", "2222");
		BasicProperties properties = new BasicProperties().builder()
				.deliveryMode(2) // 持久化消息
				.contentEncoding("UTF-8") // 编码方式
				.expiration("10000") // 过期时间
				.headers(headers) //自定义属性
				.build();*/
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.deliveryMode(2); //持久化消息
        builder.expiration("10000"); //设置消息超时时间 ,单位:ms

        //发送消息
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                builder.build(),
                message.getBytes());
//		TimeUnit.SECONDS.sleep(5);
        //关闭资源
        channel.close();
        connection.close();
    }

    private static void newsTTL() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, false, false, null);
        //创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "持久化消息1";
        //发送消息
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        BasicProperties.Builder builder = new BasicProperties.Builder();
        builder.deliveryMode(2); //持久化消息
        builder.expiration("10000"); //设置消息超时时间 ,单位:ms
        //发送消息
        message = "过期时间为10s";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                builder.build(),
                message.getBytes());
        //发送消息
        message = "过期时间为5s";
        builder.expiration("5000");
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                builder.build(),
                message.getBytes());
        //关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 优先级队列
     */
    private static void priorityQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        Map<String, Object> args = new HashMap<>();
        //设置队列的优先级，最高优先级为10
        args.put("x-max-priority", 10);
        //创建一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //创建队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, args);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        //设置每个消息的优先级
        for (int i = 0; i < 10; i++) {
            BasicProperties.Builder builder = new BasicProperties.Builder();
            builder.priority(i);
            BasicProperties properties = builder.build();
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, ("messages-" + i).getBytes());
        }
        //关闭资源
        channel.close();
        connection.close();
    }


}
