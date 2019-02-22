package currentLimiting.guava.seckill;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器
 */
public class CountUtils {
    /**
     * 假设秒杀商品总数=10
     */
    public static final AtomicInteger TOTAL_COUNT = new AtomicInteger(10);

    /**
     * 秒杀成功，商品数量减1
     */
    public static int decrease() {
        return TOTAL_COUNT.decrementAndGet();
    }

}
