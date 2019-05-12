package com.tang.kafka.topics;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class TopicsUtils {
    private static final String BROKER_URL = "192.168.56.101:9092,192.168.56.101:9093";
    private AdminClient adminClient;

    public TopicsUtils() {
        // 属性配置信息
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, BROKER_URL);
        adminClient = AdminClient.create(props);
    }

    public void listTopics() {
        ListTopicsResult result = adminClient.listTopics();
        try {
            Collection<TopicListing> topicsList = result.listings().get();
            for (TopicListing topic : topicsList) {
                System.out.printf("Topic name: %s\n", topic.name());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //TopicsUtils tu = new TopicsUtils();
        //tu.listTopics();

        //tu.describeTopic("Topic-03");
        System.out.println(Math.abs("ConsumerGroup22".hashCode()) % 50);
    }

    public void createTopic(String topicName, int partitionNum, short replicFactor) {
        NewTopic topic = new NewTopic(topicName, partitionNum, replicFactor);
        CreateTopicsResult result = adminClient.createTopics(Arrays.asList(topic));
        for (Map.Entry entry : result.values().entrySet()) {
            System.out.printf("%s, %s", entry.getKey(), entry.getValue());
        }
    }

    public void describeTopic(String topicName) {
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList(topicName));

        try {
            Map<String, TopicDescription> map = result.all().get();
            for (Map.Entry entry : map.entrySet()) {
                System.out.printf("%s, %s", entry.getKey(), entry.getValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
