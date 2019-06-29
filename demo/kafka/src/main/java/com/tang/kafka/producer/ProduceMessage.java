package com.tang.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
/**
 * 产生消息
 *  有错误
 */
public class ProduceMessage {
    private static Properties props;
    private static KafkaProducer<String, String> producer;

    private static final String TOPIC_NAME = "Topic-05";

    public static void main(String[] args) {
        // 定义Kafka服务信连接信息
        props = new Properties();
        // Kafka broker连接地址
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        // 序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //sendMessage();
        synchSendMessage();
        //asynchSendMessage();
    }

    // 默认方式发送消息
    public static void sendMessage() {
        props.put("acks", "0");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME,
                "key01", "打气娃娃01");
        try {
            System.out.println("默认方式发送");
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 同步发送
    public static void synchSendMessage() {
        producer = new KafkaProducer<>(props);

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, 2,
                "key01", "打气娃娃03");
        try {
            RecordMetadata result = producer.send(record).get();
            System.out.printf("同步发送：%s，分区：%d，offset：%d\n", result.topic(),
                    result.partition(), result.offset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
