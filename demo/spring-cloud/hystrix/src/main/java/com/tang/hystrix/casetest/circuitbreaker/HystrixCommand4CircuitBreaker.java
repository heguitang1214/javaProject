package com.tang.hystrix.casetest.circuitbreaker;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.junit.jupiter.api.Test;

/**
 * Hystrix 熔断
 * 在滚动时间窗口内，如果没有接收到指定最少请求个数，即使所有请求都失败，也不会打开断路器。
 * @author : Five-云析学院
 * @since : 2019年04月13日 21:16
 */

public class HystrixCommand4CircuitBreaker extends HystrixCommand<String> {
    private final String name;

    protected HystrixCommand4CircuitBreaker(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("circuitBreakerGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("circuitBreakerKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("circuitThreadPoolKey"))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter().withCoreSize(200)
                )
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                // 打开熔断器
//                                .withCircuitBreakerEnabled(true)
//                                //10s内至少请求10次，如果10s内没有接收到10次请求，即使所有请求都失败了，断路器也不会打开
//                                .withMetricsRollingStatisticalWindowInMilliseconds(10000)
//                                .withCircuitBreakerRequestVolumeThreshold(10)
//                                //当出错率超过50%后开启断路器.
//                                .withCircuitBreakerErrorThresholdPercentage(50)
//                                //断路器打开后的休眠时间
//                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                )
        );
        this.name = name;
    }

    @Override
    protected String getFallback() {
        System.out.println(Thread.currentThread()+"Hi This is Fallback for name:"+this.name);
//        // 当熔断后, fallback流程由main线程执行, 设置sleep, 体现熔断恢复现象.
//        try {
//            TimeUnit.MILLISECONDS.sleep(900);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return this.name;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("-----"+name);
        int num = Integer.valueOf(name);
        // 模拟执行成功

        if(num % 2 == 1) {
            System.out.println("Hi This is HystrixCommand for name:"+this.name);
            return name;
        } else {
            // 模拟异常
            while(true){}
        }
    }

    public static class UnitTest{
        @Test
        public void testHystrixCommand4CircuitBreaker(){
            final long start = System.currentTimeMillis();
            for (int i = 0; i < 50; i++) {
                try {
                    // queue() 异步调用 , execute() 同步调用
                    new HystrixCommand4CircuitBreaker(i+"").execute();
                }catch (Exception e){
                    System.out.println("run 捕获异常 ");
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
