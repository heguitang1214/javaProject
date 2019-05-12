//package com.zero.jms;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//
///**
// * @ClassName QueueConsumer
// * @Description TODO
// * @Author 云析-路飞
// * @Date 2018/5/7 14:46
// * @Version 1.0
// */
//public class QueueConsumer {
//    private static final String USERNAME= ActiveMQConnectionFactory.DEFAULT_USER;
//    private static final String PASSWORD=ActiveMQConnectionFactory.DEFAULT_PASSWORD;
//    private static final String URL=ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
//
//    public static void main(String[] args) {
//        ConnectionFactory connectionFactory;
//        Connection connection = null;
//        Session session;
//        Destination destination;
//        //消息的消费者
//        MessageConsumer messageConsumer;
//        //实例化工厂
//        connectionFactory =new ActiveMQConnectionFactory(QueueConsumer.USERNAME, QueueConsumer.PASSWORD, QueueConsumer.URL);
//
//        try {
//            //创建连接
//            connection =connectionFactory.createConnection();
//            connection.start();
//            //创建Session
//            session =connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
//            //创建队列,去目的地接数据
//            destination =session.createQueue("FirstQueue");
//            //创建消费者
//            messageConsumer =session.createConsumer(destination);
//            while(true){
//                TextMessage message =(TextMessage) messageConsumer.receive(100000);
//                if(message !=null){
//                    System.out.println("收到的消息："+message.getText());
//                }else{
//                    break;
//                }
//            }
//        } catch (JMSException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//}
