package currentLimiting.baseCode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 接口限流
 */
public class Monitor {

    //请求次数
    private final AtomicInteger requestCount = new AtomicInteger(0);

    //聚合时间
    private volatile long aggregation = 0;

    //时间间隔，单位ms
    private final int interval = 60 * 1000;

    //最大访问次数
    private final int limit = 100;

    private final String methodName;

    public Monitor(String methodName) {
        this.methodName = methodName;
    }

    public void checkRequestCount() throws Exception {
        long current = System.currentTimeMillis();
        long time = current / interval * interval; //按分钟取整

        synchronized (this) {
            if (aggregation == time) {
                if (requestCount.get() >= limit) {
                    throw new Exception(methodName + "超过最大调用次数!当前已经调用" + requestCount.get() + "次");
                } else {
                    requestCount.incrementAndGet();
                }
            } else {
                requestCount.set(1);
                aggregation = time;
            }
        }
    }
}
