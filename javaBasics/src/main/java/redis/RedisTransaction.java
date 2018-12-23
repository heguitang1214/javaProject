package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Redis事务操作
 **/
public class RedisTransaction {

    private void submitOrder(long userId, int amount) {
        Jedis client = new Jedis("47.93.194.11", 6379);
        client.auth("heguitang");
        try {
            client.watch("iPhoneX");

            //判断商品是否已经卖完
            if (Integer.parseInt(client.get("iPhoneX")) < 1) {
                System.err.println("商品已卖完!");
                client.unwatch();
            }

            //商品库存减少，用户订单增加记录
            Transaction trans = client.multi();
            trans.decrBy("iPhoneX", amount);
            trans.zadd("orders:" + userId, amount, "iPhone");
            trans.incrBy("account:"+userId,1000);
            //执行事务
            List<Response<?>> result = trans.execGetResponse();

            if (result.size() == 0) {
                System.err.println("用户-" + userId + ":购买失败!");
                return;
            }

            for (Response<?> response : result) {
                System.out.println("用户-" + userId + ":" + response.get());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            client.close();
        }
    }

    public static void main(String[] args) {
        final RedisTransaction transaction = new RedisTransaction();
        Jedis client = new Jedis("47.93.194.11", 6379);
        client.auth("heguitang");
        try {
            client.flushDB();
            //初始化商品数据
            client.set("iPhoneX", "10");
        } finally {
            client.close();
        }

        new Thread(new Runnable() {
            public void run() {
                transaction.submitOrder(1l, 2);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                transaction.submitOrder(2l, 1);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                transaction.submitOrder(3l, 3);
            }
        }).start();

    }
}
