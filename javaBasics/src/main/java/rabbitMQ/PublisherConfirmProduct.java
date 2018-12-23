package rabbitMQ;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 消息确认机制
 */
public class PublisherConfirmProduct {

    public static void main(String[] args) throws Exception {
//		commonConfirm(); //普通确认机制，单条发送确认
//		batchConfirm(); //批量确认机制
        asyncConfirm(); //异步确认机制
    }

    private static final String EXCHANGE_NAME = "demo.exchange"; //交换器名称
    private static final String ROUTING_KEY = "demo.routingkey"; //路由键
    private static final String QUEUE_NAME = "demo.queue"; //队列名称
    private static final String IP_ADDRESS = "47.93.194.11";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号为 5672

    public static void commonConfirm() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个 type="direct" 、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //发送一条持久化的消息: hello world !
        String message = "Hello World !";

        //开启发送方确认机制
        channel.confirmSelect();
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            if (channel.waitForConfirms()) { // 逐条确认是否发送成功
                System.out.println("send success!" + (i + 1));
            }
        }
        //关闭资源
        channel.close();
        connection.close();
    }

    public static void batchConfirm() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个 type="direct" 、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //发送一条持久化的消息: hello world !
        String message = "Hello World !";

        //开启发送方确认机制
        channel.confirmSelect();
        for (int i = 0; i < 100; i++) {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
        }
        if (channel.waitForConfirms()) { // 批量确认是否发送成功   如果某一次确认失败   这一批都要重新发送
            System.out.println("send success!");
        }
        //关闭资源
        channel.close();
        connection.close();
    }

    public static void asyncConfirm() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("rabbitstudy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个 type="direct" 、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        //创建一个持久化、非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //将交换器与队列通过路由键绑定
//        channel.basicQos(1); //设置未被确认消费的个数
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        //发送一条持久化的消息: hello world !
        String message = "Hello World !";

        //开启发送方确认机制
        channel.confirmSelect();

        //定义一个未确认消息集合
        final SortedSet<Long> unConfirmSet = Collections.synchronizedNavigableSet(new TreeSet<>());
        //添加消息确认监听器
        channel.addConfirmListener(new ConfirmListener() {
            public void handleNack(long deliveryTag, boolean multiple) {
                System.out.println("拒绝消息 deliveryTag:" + deliveryTag + " multiple:" + multiple);
            }

            public void handleAck(long deliveryTag, boolean multiple) {
                System.out.println("确认消息 deliveryTag:" + deliveryTag + " multiple:" + multiple);
                if (multiple) {
                    //multiple为true，则deliveryTag之前的所有u消息全部被确认
                    unConfirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    //否则只确认一条消息
                    unConfirmSet.remove(deliveryTag);
                }
            }
        });
        for (int i = 0; i < 100; i++) {
            long deliveryTag = channel.getNextPublishSeqNo(); //得到消息的deliveryTag
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes());
            //将发送的消息保存到集合中，来做异步的确认
            unConfirmSet.add(deliveryTag);
        }

        TimeUnit.SECONDS.sleep(10);
        System.out.println(unConfirmSet.size());
        //关闭资源
        channel.close();
        connection.close();
    }

}
