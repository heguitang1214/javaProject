package currentLimiting.guava.seckill;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * Guava秒杀场景：Guava使得商品被均匀的被秒杀，效果较好，未秒到商品的用户引流至推荐页
 */
public class GuavaSecKill {

    public static void main(String[] args) throws InterruptedException {
        //限流，每秒允许10个请求进入秒杀 QPS=10 令牌生成速度=100ms/个
        RateLimiter limiter = RateLimiter.create(10);

        for (int i = 0; i < 100; i++) {
            //100个线程同时抢购
            new Thread(() -> {
                //每个秒杀请求如果在50ms（0.05s）以内得到令牌，就算是秒杀成功，否则就返回秒杀失败
                if (limiter.tryAcquire(50, TimeUnit.MILLISECONDS)) {
                    if (CountUtils.TOTAL_COUNT.get() > 0) {
                        //库存减1
                        CountUtils.decrease();
                        System.out.println("恭喜您，秒杀成功！！！");
                    } else {
                        System.out.println("秒杀失败，商品已售完");
                    }

                } else {
                    System.out.println("秒杀失败，请继续努力~");
                }
            }).start();
            //给limiter 0.01s的时间生成新的令牌生成
            Thread.sleep(10);
        }
    }
}
