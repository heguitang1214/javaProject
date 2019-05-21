package com.tang.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.*;

class ConsumerHandler implements Runnable {
    private ConsumerRecord<String, String> record;

    public ConsumerHandler(ConsumerRecord<String, String> consumerRecord) {
        this.record = consumerRecord;
    }

    @Override
    public void run() {
        System.out.printf("topic=%s, partition=%d, offset=%d, key=%s, value=%s\n",
                record.topic(), record.partition(), record.offset(), record.key(), record.value());
    }
}

public class ConsumerThreadPool {
    private KafkaConsumer<String, String> consumer;

    // Consumer线程池
    private ExecutorService executor;
    // 线程数量
    private int threadNum;

    public ConsumerThreadPool(int threadNum) {
        this.threadNum = threadNum;
        executor = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

        Properties props = new Properties();
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        props.put("group.id", "ConsumerGroup1");

        // 是否自动提交偏移量
        props.put("enable.auto.commit", "true");
        // 自动确认offset的时间间隔
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        // Consumer在ZooKeeper中发现没有初始offset时或者发现offset非法时的行为
        props.put("auto.offset.reset", "earliest");

        // 序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singletonList("Topic-03"));
    }

    public void start() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                executor.submit(new ConsumerHandler(record));
            }
        }
    }

    public static void main(String[] args) {
        ConsumerThreadPool consumerThreadPool = new ConsumerThreadPool(3);
        consumerThreadPool.start();
    }
}
