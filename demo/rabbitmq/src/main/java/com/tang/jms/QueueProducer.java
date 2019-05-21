//package com.zero.jms;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//
//import javax.jms.*;
//
///**
// * @ClassName QueueProducer
// * @Description TODO
// * @Author 云析-路飞
// * @Date 2018/5/7 14:40
// * @Version 1.0
// */
//public class QueueProducer {
//    //默认的连接用户名
//    private static final String USERNAME= ActiveMQConnectionFactory.DEFAULT_USER;
//    //默认的连接密码
//    private static final String PASSWORD=ActiveMQConnectionFactory.DEFAULT_PASSWORD;
//    //默认的连接地址
//    private static final String URL=ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
//    //发送消息的条数
//    private static final int MessageNum=10;
//
//    public static void main(String[] args) {
//        ConnectionFactory connectionFactory;//连接工厂生产Connection
//        Connection connection = null;
//        //发送或者接收的线程
//        Session session;
//        //消息的目得地
//        Destination destination;
//        //消息生产者
//        MessageProducer messageProducer;
//        //实例化连接工厂
//        connectionFactory =new ActiveMQConnectionFactory(QueueProducer.USERNAME, QueueProducer.PASSWORD, QueueProducer.URL);
//        //通过连接工厂获取连接
//        try {
//            //创建连接
//            connection =connectionFactory.createConnection();
//            connection.start();
//            //创建Session
//            session =connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
//            //创建队列，生成一个目的地
//            destination =session.createQueue("FirstQueue");
//            //创建生成者
//            messageProducer =session.createProducer(destination);
//            //发送消息
//            sendMessage(session,messageProducer);
//            session.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            if(connection !=null){
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    //发送消息
//    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
//        for(int i=0;i<QueueProducer.MessageNum;i++){
//            TextMessage message =session.createTextMessage("ActiveMQ发送的消息 "+i);
//            System.out.println("发送消息：ActiveMq发送的消息"+i);
//            messageProducer.send(message);
//        }
//    }
//}
