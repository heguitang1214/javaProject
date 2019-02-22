package currentLimiting.guava.smoothbursty;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 平滑突发限流(SmoothBursty)
 */
public class SmoothBurstyRateLimitTest01 {

    public static void main(String[] args) {
        //每秒允许5个请求，表示桶容量为5且每秒新增5个令牌，即每隔0.2秒新增一个令牌
        RateLimiter limiter = RateLimiter.create(5);//设置QPS
        //如果当前桶中有足够令牌则成功（返回值为0）返回获取token的耗时，以秒为单位
        //将突发请求速率平均为了固定请求速率，固定频率=0.2s/个
        System.out.println(limiter.acquire(1));//获取令牌所消耗的时间
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
        System.out.println(limiter.acquire(1));
    }
}
