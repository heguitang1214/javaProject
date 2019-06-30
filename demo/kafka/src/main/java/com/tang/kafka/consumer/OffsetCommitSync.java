package com.tang.kafka.consumer;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class OffsetCommitSync {
    private static final String TOPIC_NAME = "Topic-05";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        props.put("group.id", "ConsumerGroup1");
        /* 关闭自动确认选项 */
        props.put("enable.auto.commit", false);
        //props.put("auto.commit.interval.ms", "1000"); // 不需要设置自动提交的事件间隔
        props.put("session.timeout.ms", "30000");
        // 序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));


        for (; ; ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());

                try {
                    // 注释这一段，就代表不提交偏移量，消费者只进行消费，每次运行都会【重复消费消息】。
                    consumer.commitSync();
                } catch (CommitFailedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
