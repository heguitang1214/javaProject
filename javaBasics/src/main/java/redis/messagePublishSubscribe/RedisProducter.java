package redis.messagePublishSubscribe;

import redis.clients.jedis.Jedis;

/**
 *  redis生产商
 */
public class RedisProducter {
    public static void main(String[] args) {
        RedisProducter producter = new RedisProducter();
        producter.product();
    }

    private void product(){
        Jedis client = new Jedis("47.93.194.11", 6379);
        client.auth("");
        client.publish("news.sport", "英超最新战报 曼联1-3曼城");
        client.publish("news.game", "网易暴雪合作手游《暗黑》明年上市");
        client.publish("news.weather", "上海11.17日 晴转多云 13~16℃");
        client.publish("news.sport", "close");
    }

}
