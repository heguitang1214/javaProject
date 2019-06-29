package com.tang.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * 产生消息
 * 自动建立分区，产生对应的消息
 */
public class ProduceMessage1 {
    private static Properties props;
    private static KafkaProducer<String, String> producer;


    public static void main(String[] args) {
        // 定义Kafka服务信连接信息
        props = new Properties();
        // Kafka broker连接地址
        props.put("bootstrap.servers", "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");

        // 序列化类【序列化】
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);

        sendMessage();
        syncSendMessage();
        asyncSendMessage();
    }


    /**
     * 默认发送消息
     */
    public static void sendMessage() {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "Topic-01", "key01", "value-卡哇伊04");
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 同步发送
     */
    public static void syncSendMessage() {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "Topic-01", "key01", "value-卡哇伊05");
        try {
            RecordMetadata result = producer.send(record).get();
            System.out.println(result.topic() + "," + result.offset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步发送
     */
    public static void asyncSendMessage() {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "Topic-01", "key01", "value-卡哇伊06");
        try {
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata result, Exception e) {
                    if (result != null) {
                        System.out.println(result.topic() + "," + result.offset());
                    } else {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
