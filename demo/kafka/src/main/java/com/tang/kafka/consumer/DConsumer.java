package com.tang.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * kafka消费指定分区的内容
 */
public class DConsumer {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        prop.put("group.id", "test8");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //如果是之前存在的group.id
        Consumer consumer = new KafkaConsumer(prop);
        TopicPartition p = new TopicPartition("test2", 2);
        //指定消费topic的那个分区
        consumer.assign(Arrays.asList(p));
        //指定从topic的分区的某个offset开始消费
        //consumer.seekToBeginning(Arrays.asList(p));
        consumer.seek(p, 5);
        //consumer.subscribe(Arrays.asList("test2"));

        //如果是之前不存在的group.id
//        Map<TopicPartition, OffsetAndMetadata> hashMaps = new HashMap<TopicPartition, OffsetAndMetadata>();
//        hashMaps.put(new TopicPartition("test2", 0), new OffsetAndMetadata(0));
//        consumer.commitSync(hashMaps);
//        consumer.subscribe(Arrays.asList("test2"));

        while (true) {
            ConsumerRecords<String, String> c = consumer.poll(100);
            for (ConsumerRecord<String, String> c1 : c) {
                System.out.println("Key: " + c1.key() + " Value: " + c1.value() + " Offset: " + c1.offset() + " Partitions: " + c1.partition());

            }
        }
    }
}
