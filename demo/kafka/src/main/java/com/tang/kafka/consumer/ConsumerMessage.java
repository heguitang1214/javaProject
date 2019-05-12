package com.tang.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerMessage {
    private static final String TOPIC_NAME = "Topic-05";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        props.put("group.id", "ConsumerGroup3");
        /* 是否自动确认offset */
        props.put("enable.auto.commit", "true");
        /* 自动确认offset的时间间隔 */
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");

        // 序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        try {
            for (; ; ) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("消费消息：topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
        } finally {
            consumer.close();
        }
    }
}
