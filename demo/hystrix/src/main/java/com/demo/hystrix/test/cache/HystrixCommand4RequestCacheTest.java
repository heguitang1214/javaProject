package com.demo.hystrix.test.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author: 无双老师
 * @Date: 2018/11/10
 * @Description:
 * cache只有同在一个context中才生效
 * 通过HystrixRequestContext.initializeContext()初始化context，通过shutdown()关闭context
 */
public class HystrixCommand4RequestCacheTest extends HystrixCommand<Boolean> {

    private final int key;
    private final String value;

    protected HystrixCommand4RequestCacheTest(int key, String value) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestCacheCommandGroup"));
        this.key = key;
        this.value = value;
    }

    // 返回结果是cache的value
    @Override
    protected Boolean run() {
        return key == 0 || key % 2 == 0;
    }

    // 构建cache的key
    @Override
    protected String getCacheKey() {
        return String.valueOf(key) + value;
    }

    public static class UnitTest {

        /**
         * 测试不使用缓存的场景
         */
        @Test
        public void testWithoutCacheHits() {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                assertTrue(new HystrixCommand4RequestCacheTest(2,"HystrixCommand4RequestCacheTest").execute());
                assertFalse(new HystrixCommand4RequestCacheTest(1,"HystrixCommand4RequestCacheTest").execute());
                assertTrue(new HystrixCommand4RequestCacheTest(0,"HystrixCommand4RequestCacheTest").execute());
                assertTrue(new HystrixCommand4RequestCacheTest(58672,"HystrixCommand4RequestCacheTest").execute());
            } finally {
                context.shutdown();
            }
        }

        /**
         * 测试使用缓存的场景
         */
        @Test
        public void testWithCacheHits() {
            //同一个请求上下文中
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                HystrixCommand4RequestCacheTest command2a = new HystrixCommand4RequestCacheTest(2,"HystrixCommand4RequestCacheTest");
                HystrixCommand4RequestCacheTest command2b = new HystrixCommand4RequestCacheTest(2,"HystrixCommand4RequestCacheTest");
                HystrixCommand4RequestCacheTest command2c = new HystrixCommand4RequestCacheTest(2,"NotCache");

                assertTrue(command2a.execute());
                // 第一次请求，不可能命中缓存
                assertFalse(command2a.isResponseFromCache());

                assertTrue(command2b.execute());
                // 命中缓存
                assertTrue(command2b.isResponseFromCache());
                
                assertTrue(command2c.execute());
                //未命中缓存
                assertFalse(command2c.isResponseFromCache());
            } finally {
                context.shutdown();
            }

            // 开启一个新的请求
            context = HystrixRequestContext.initializeContext();
            try {
                HystrixCommand4RequestCacheTest command3a = new HystrixCommand4RequestCacheTest(2,"HystrixCommand4RequestCacheTest");
                HystrixCommand4RequestCacheTest command3b = new HystrixCommand4RequestCacheTest(2,"HystrixCommand4RequestCacheTest");
                assertTrue(command3a.execute());
                // 新的请求上下文中不会命中上一个请求中的缓存
                assertFalse(command3a.isResponseFromCache());
                // 从新的请求上下文中command3a.execute()执行中得到的cache
                command3b.execute();
                assertTrue(command3b.isResponseFromCache());
            } finally {
                context.shutdown();
            }
        }
    }

}
