package com.demo.hystrix.test.circuitbreak;

import com.netflix.hystrix.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: 无双老师
 * @Date: 2018/11/10
 * @Description:
 * CircuitBreakerRequestVolumeThreshold设置为3，意味着10s内请求超过3次就触发熔断器
 * run()中无限循环使命令超时进入fallback，执行3次run后，将被熔断，进入降级，即不进入run()而直接进入fallback
 * 如果未熔断，但是threadpool被打满，仍然会降级，即不进入run()而直接进入fallback
 */
public class HystrixCommand4CircuitBreakerTest extends HystrixCommand<String> {

    private final String name;

    public HystrixCommand4CircuitBreakerTest(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CircuitBreakerTestGroup"))  
                .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreakerTestKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CircuitBreakerTest"))
                .andThreadPoolPropertiesDefaults(	// 配置线程池
                		HystrixThreadPoolProperties.Setter()
						// 配置线程池里的线程数，设置足够多线程，以防未熔断却打满线程池而出发降级
                		.withCoreSize(200)
                )
                .andCommandPropertiesDefaults(	// 配置熔断器
                		HystrixCommandProperties.Setter()
                		.withCircuitBreakerEnabled(true)
						//10s内至少请求3次
						.withMetricsRollingStatisticalWindowInMilliseconds(10000)
                		.withCircuitBreakerRequestVolumeThreshold(3)
						//默认:50%。当出错率超过50%后熔断器启动.
                		.withCircuitBreakerErrorThresholdPercentage(50)
//                		.withCircuitBreakerForceOpen(true)	// 置为true时，所有请求都将被拒绝，直接到fallback
//                		.withCircuitBreakerForceClosed(true)	// 置为true时，将忽略错误
//                		.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)	// 信号量隔离
//                		.withExecutionTimeoutInMilliseconds(5000)
                )
        );
        this.name = name;
    }

	/**
	 * 正常逻辑
	 * @return
	 * @throws Exception
	 */
	@Override
    protected String run() throws Exception {
    	int num = Integer.valueOf(name);
		// 模拟执行成功
    	if(num % 2 == 0 && num < 10) {
    		return "success: " + name;
    	} else {
			// 模拟异常
            while (true) {

            }
    	}
    }

	/**
	 * 异常逻辑
	 * @return
	 */
	@Override
    protected String getFallback() {
        return "CircuitBreaker fallback: " + name;
    }

    public static class UnitTest {

        @Test
        public void testSynchronous() throws IOException {
        	for(int i = 0; i < 50; i++) {
	        	try {
	        		System.out.println(new HystrixCommand4CircuitBreakerTest(String.valueOf(i)).execute());
	        	} catch(Exception e) {
	        		System.out.println("run()抛出HystrixBadRequestException时，被捕获到这里" + e.getCause());
	        	}
        	}
        }
    }

}