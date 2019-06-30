package com.tang.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

/**
 * 监控分区再均衡
 */
public class RebalanceListener {
    private static final String TOPIC_NAME = "Topic-05";

    static Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    static KafkaConsumer<String, String> consumer;

    private static class CustomerRebalancer implements ConsumerRebalanceListener {
        /**
         * 再均衡开始之前和消费者停止读取消息之后被调用
         * 如果在这里提交偏移量，下一个接管分区的消费者就知道该从哪里开始读取了
         */
        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            // 如果发生再均衡，我们要在即将失去分区所有权时提交偏移量
            // 要注意，提交的是最近处理过的偏移量，而不是批次中还在处理的最后一个偏移量
            System.out.println("Lost partitions in rebalance. Committing current offsets:" + currentOffsets);
            consumer.commitSync(currentOffsets);
        }

        /**
         * 在重新分配分区之后和新的消费者开始读取消息之前被调用
         */
        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            long committedOffset = -1;
            for (TopicPartition topicPartition : partitions) {
                // 获取该分区已经消费的偏移量
                committedOffset = consumer.committed(topicPartition).offset();
                // 重置偏移量到上一次提交的偏移量的下一个位置处开始消费
                consumer.seek(topicPartition, committedOffset + 1);
            }
        }
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        props.put("group.id", "ConsumerGroup1");
        /* 关闭自动确认选项 */
        props.put("auto.commit.offset", false);
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");

        // 序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME), new CustomerRebalancer());

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("消费消息：topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                }
                consumer.commitAsync(currentOffsets, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                consumer.commitSync(currentOffsets);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                consumer.close();
                System.out.println("Closed consumer successfully!");
            }
        }
    }
}
