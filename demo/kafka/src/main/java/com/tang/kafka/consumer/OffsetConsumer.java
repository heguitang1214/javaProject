package com.tang.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class OffsetConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        props.put("group.id", "ConsumerG22");
        /* 是否自动确认offset */
        props.put("enable.auto.commit", "true");
        /* 自动确认offset的时间间隔 */
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");

        // 序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.assign(Arrays.asList(new TopicPartition("Topic-06", 1)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition("Topic-06", 1)));
        //consumer.seek(new TopicPartition("Topic-05", 1), 9);

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
