package com.tang.kafka.example;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentProducer {
    // 定义Kafka topic信息
    private static final String topic = "Topic-14";

    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        // 定义Kafka服务信连接信息
        Properties props = new Properties();
        // Kafka server连接地址
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        // producer需要server接收到数据之后发出的确认接收的信号。all代表需要等待所有备份都成功写入
        props.put("acks", "all");
        // 重试机制，0的值将使客户端不重新发送数据。允许重试将潜在的改变数据的顺序
        props.put("retries", "0");
        // producer将试图批处理消息记录，以减少请求次数。用以客户端降低请求的数量
        props.put("batch.size", 16384);
        // producer发送消息延迟
        props.put("linger.ms", 1);
        // producer用来缓存数据的内存大小
        props.put("buffer.memory", 33554432);
        // 序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        PaymentGenerator pg = new PaymentGenerator();
        KafkaProducer<Object, Object> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            String msg = pg.generatePayment().toString();
            ProducerRecord<Object, Object> record = new ProducerRecord<Object, Object>(topic, msg);
            producer.send(record);
        }

        System.out.println("======== finish");
        producer.close();
    }
}
