package com.tang.kafka.stream;


import com.google.common.util.concurrent.AtomicDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 流式处理
 */
public class SparkStreaming {

    private static Logger logger = LoggerFactory.getLogger(SparkStreaming.class);

    private static AtomicLong paymentCount = new AtomicLong();

    private static AtomicDouble totalPrice = new AtomicDouble();

    private static final String topic = "my_topic02";




    public static void main(String[] args) {
        //初始化spark上下文
//        JavaStreamingContext sc = SparkUtil.getJavaStreamingContext("realtime-stat","local[2]",
//                null, Durations.seconds(1));

        Set<String> topicsSet = new HashSet<>(Arrays.asList(topic));

        Map<String, String> kafkaParams = new HashMap<>();
        // kafka Server连接地址
        kafkaParams.put("bootstrap.servers", "master:9092,master:9093,master:9094");
        // 消息消费偏移量，smallest代表从topic的开始位置消费所有的消息
        kafkaParams.put("auto.offset.reset", "smallest");
        //序列化定义
        kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");




    }

}
