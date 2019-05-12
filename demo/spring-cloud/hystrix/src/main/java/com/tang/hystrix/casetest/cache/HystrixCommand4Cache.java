package com.tang.hystrix.casetest.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

/**
 * Hystrix 请求缓存
 * 如何判断请求是否从缓存获取值? ---根据请求参数判断,如果参数相同,则返回值相同,可取缓存结果
 *
 * @author : Five-云析学院
 * @since : 2019年04月15日 15:21
 */

public class HystrixCommand4Cache extends HystrixCommand<Boolean> {

    private final int key;
    private final String value;

    protected HystrixCommand4Cache(int key, String value) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CacheGroup"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)
                )
        );
        this.key = key;
        this.value = value;
    }

    // 返回结果是cache的value
    @Override
    protected Boolean run() {
    	System.out.println(" This is Run... ");
//        int i=1/0;
        return true;
    }

    // 构建cache的key
    // 如果调用getCacheKey 得到的结果是相同的, 说明是相同的请求  可以走缓存
    @Override
    protected String getCacheKey() {
        return String.valueOf(key);
    }

    @Override
    protected Boolean getFallback() {
        System.out.println("fallback");
        return false;
    }

    public static class UnitTest {
        @Test
        public void testHystrixCommand4Cache() {
            //同一个请求上下文中：需要开启
            HystrixRequestContext.initializeContext();
            HystrixCommand4Cache command2a = new HystrixCommand4Cache(2,"HystrixCommand4RequestCacheTest");
            HystrixCommand4Cache command2b = new HystrixCommand4Cache(2,"HystrixCommand4RequestCacheTest");
            HystrixCommand4Cache command2c = new HystrixCommand4Cache(2,"NotCache");
            System.out.println("command2a:"+command2a.execute());
            // 第一次请求，不可能命中缓存
            System.out.println("第1次请求是否命中缓存："+command2a.isResponseFromCache());
            System.out.println("command2b:"+command2b.execute());
            // 命中缓存
            System.out.println("第2次请求是否命中缓存："+command2b.isResponseFromCache());
            System.out.println("command2c:"+command2c.execute());
            //未命中缓存
            System.out.println("第3次请求是否命中缓存："+command2c.isResponseFromCache());

            // 开启一个新的请求
            HystrixRequestContext.initializeContext();
            HystrixCommand4Cache command3a = new HystrixCommand4Cache(2,"HystrixCommand4RequestCacheTest");
            HystrixCommand4Cache command3b = new HystrixCommand4Cache(2,"HystrixCommand4RequestCacheTest");
            System.out.println("command3a:"+command3a.execute());
            // 新的请求上下文中不会命中上一个请求中的缓存
            System.out.println("第4次请求是否命中缓存："+command3a.isResponseFromCache());
            // 从新的请求上下文中command3a.execute()执行中得到的cache
            System.out.println("command3b:"+command3b.execute());
            System.out.println("第5次请求是否命中缓存："+command3b.isResponseFromCache());
        }
    }

}
