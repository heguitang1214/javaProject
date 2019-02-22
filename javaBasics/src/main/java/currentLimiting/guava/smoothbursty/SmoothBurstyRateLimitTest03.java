package currentLimiting.guava.smoothbursty;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 平滑突发限流(SmoothBursty) 使用场景：对冷数据的预热处理
 */
public class SmoothBurstyRateLimitTest03 {

    public static void main(String[] args) {
        //每秒允许5个请求，表示桶容量为5且每秒新增5个令牌，即每隔0.2秒新增一个令牌
        RateLimiter limiter = RateLimiter.create(5);
        //第一秒突发了10个请求
        System.out.println(limiter.acquire(10));
        //limiter.acquire(1)将等待差不多2秒桶中才能有令牌，0.2 * 10 = 2s
        System.out.println(limiter.acquire(1));
        //固定速率
        System.out.println(limiter.acquire(1));
        //固定速率
        System.out.println(limiter.acquire(1));
        //固定速率
        System.out.println(limiter.acquire(1));
    }
}
