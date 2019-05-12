package com.tang.hystrix.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.tang.hystrix.service.HystrixCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * Hystrix @HystrixCommand
 *
 * @author : Five-云析学院
 * @since : 2019年04月15日 20:55
 */
@RestController
public class HystrixDemoController {

    /**
     * 线程隔离之线程池隔离
     * @return
     */
    @GetMapping("/testThread")
    @HystrixCommand(
            groupKey = "ThreadPoolGroupKey",
            commandKey = "ThreadPoolCommandKey",
            threadPoolKey = "ThreadPoolKey",
            fallbackMethod = "fallbackMethod",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.strategy", value="THREAD")
            }
            , threadPoolProperties = {
            		@HystrixProperty(name="coreSize",value="15")
            }
    )
    public String testThread(){
        try {
            int m = new Random().nextInt(1200);
            System.out.println("Thread sleep "+m+" ms");
            TimeUnit.MILLISECONDS.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread Pool";
    }

    /**
     * 线程隔离之信号量隔离
     * @return
     */
    @GetMapping("/testSemaphore")
    @HystrixCommand(
            groupKey = "SemaphoreGroupKey",
            commandKey = "SemaphoreCommandKey",
            threadPoolKey = "SemaphoreThreadPoolKey",
            fallbackMethod = "fallbackMethod",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
                    @HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests", value="3"),
                    @HystrixProperty(name="fallback.isolation.semaphore.maxConcurrentRequests", value="1")
            }
    )
    public String testSemaphore(){
        try {
            int m = new Random().nextInt(1200);
            System.out.println("Thread sleep "+m+" ms");
            TimeUnit.MILLISECONDS.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Semaphore";
    }

    /**
     * 熔断
     * @return
     */
    @GetMapping("/testCircuitBreaker")
    @HystrixCommand(
            groupKey = "CircuitBreakerGroupKey",
            commandKey = "CircuitBreakerCommandKey",
            threadPoolKey = "CircuitBreakerThreadPoolKey",
            fallbackMethod = "fallbackMethod",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value="200")
            },
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="circuitBreaker.enabled",value="true"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="10000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="8"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000")
            }
    )
    public String testCircuitBreaker(){
        try {
            int value = new Random().nextInt(10);
            System.out.println("Random value "+value);
            if(value % 3 !=0){
                while(true){}
            }else{
                System.out.println("secuss for "+value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "CircuitBreaker";
    }

    /***
     * Fallback
     * @return
     */
    public String fallbackMethod(){
        System.out.println("This is fallbackMethod -> "+Thread.currentThread());
        return "fault";
    }


	@Autowired
	private HystrixCacheService hystrixCacheService;
	
	/**
	 * 请求缓存
	 * @return
	 */
	@GetMapping("/testCache")
	public String testCache(){
		String result1 = hystrixCacheService.randomMethod(1000);
		String result2 = hystrixCacheService.randomMethod(1000);
		
	    return "Result1:"+result1+"====="+"Result2:"+result2;
	}

}
