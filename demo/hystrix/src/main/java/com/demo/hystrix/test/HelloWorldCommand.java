package com.demo.hystrix.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * hello world入门
 */
public class HelloWorldCommand extends HystrixCommand<String> {

    private final String name;

    public HelloWorldCommand(String name) {
        //最少配置:指定命令组名(CommandGroup)
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        // 依赖逻辑封装在run()方法中
        return "Hello " + name + "!";
    }

    public static class UnitTest {

        /**
         * 同步方式
         */
        @Test
        public void testSynchronous() {
            assertEquals("Hello World!", new HelloWorldCommand("World").execute());
            assertEquals("Hello Bob!", new HelloWorldCommand("Bob").execute());
        }

        /**
         * 异步方式
         * @throws Exception
         */
        @Test
        public void testAsynchronous1() throws Exception {
            assertEquals("Hello World!", new HelloWorldCommand("World").queue().get());
            assertEquals("Hello Bob!", new HelloWorldCommand("Bob").queue().get());
        }

        /**
         * 异步方式
         * @throws Exception
         */
        @Test
        public void testAsynchronous2() throws Exception {

            Future<String> fWorld = new HelloWorldCommand("World").queue();
            Future<String> fBob = new HelloWorldCommand("Bob").queue();

            assertEquals("Hello World!", fWorld.get());
            assertEquals("Hello Bob!", fBob.get());
        }

        @Test
        public void testObservable() throws Exception {

            Observable<String> fWorld = new HelloWorldCommand("World").observe();
            Observable<String> fBob = new HelloWorldCommand("Bob").observe();

            // 阻塞
            assertEquals("Hello World!", fWorld.toBlocking().single());
            assertEquals("Hello Bob!", fBob.toBlocking().single());

            // 非阻塞
            fWorld.subscribe(new Observer<String>() {

                @Override
                public void onCompleted() {
                    // nothing needed here
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }


                @Override
                public void onNext(String v) {
                    System.out.println("onNext: " + v);
                }

            });

            // 非阻塞
            fBob.subscribe(new Action1<String>() {

                @Override
                public void call(String v) {
                    System.out.println("onNext: " + v);
                }

            });

        }
    }

}
