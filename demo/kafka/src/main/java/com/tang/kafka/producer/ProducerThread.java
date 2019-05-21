package com.tang.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class ProducerThread implements Runnable {
    private static KafkaProducer<String, String> producer;
    private static final String topic = "Topic-03";

    public ProducerThread() {
        // 定义Kafka服务信连接信息
        Properties props = new Properties();
        // Kafka broker连接地址
        props.put("bootstrap.servers",
                "192.168.56.101:9092,192.168.56.101:9093,192.168.56.101:9094");
        // leader/follower 一个分区3个副本（冗余）
        // all/-1，1个leader，2follower全部写入了，才能算成功
        // acks=1, leader写入成功，代表消息发送成功
        // acks=0, 发送了不管，不管成功与否（producer发送的三种模式之一）
        props.put("acks", "all");
        // producer自动重试失败的发送次数
        props.put("retries", 0);
        // producer批量发送的基本单位，默认是16384Bytes，即16kB
        props.put("batch.size", 16384);
        // 发送线程在检查batch是否ready的时候，判断有没有过期的参数，默认大小是0ms
        props.put("linger.ms", 1);
        // Producer会创建一个BufferPool，totalSize为buffer.memory。
        // pool里会创建很多batch，每个batch大小就是batch.size
        props.put("buffer.memory", 33554432);
        // 序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(props);
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            String message = "打气娃娃" + String.valueOf(++i);
            producer.send(new ProducerRecord<String, String>(topic, message), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    System.out.printf("发送消息==>分区：%d，Offset：%d\n", metadata.partition(),
                            metadata.offset());
                }
            });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(new ProducerThread());
        producerThread.start();
    }
}
