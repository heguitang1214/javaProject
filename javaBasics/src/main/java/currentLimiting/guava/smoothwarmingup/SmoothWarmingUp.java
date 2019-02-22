package currentLimiting.guava.smoothwarmingup;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 平滑预热限流(SmoothWarmingUp)
 */
public class SmoothWarmingUp {

    public static void main(String[] args) {
        //permitsPerSecond:每秒新增的令牌数  warmupPeriod:从冷启动速率过渡到平均速率的时间间隔
        //系统冷启动后慢慢的趋于平均固定速率（即刚开始速率慢一些，然后慢慢趋于我们设置的固定速率）
        RateLimiter limiter = RateLimiter.create(10, 1000, TimeUnit.MILLISECONDS);
        for(int i = 0; i < 20;i++) {
            //获取一个令牌
            System.out.println(limiter.acquire(1));
        }
    }
}
