package com.demo.hystrix.test.collapsing;

import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 相邻两个请求可以自动合并的前提是两者足够“近”：启动执行的间隔时间足够小，默认10ms
 * 
 */
public class HystrixCommand4RequestCollapsingTest {
        @Test
        public void testCollapser() throws Exception {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
            	
                Future<String> f1 = new HelloWorldHystrixCollapser(1).queue();
                Future<String> f2 = new HelloWorldHystrixCollapser(2).queue();
                // 这条很可能会合并到f1和f2的批量请求中，因为execute是阻塞的
//                System.out.println("execute强制阻塞 : " + new HelloWorldHystrixCollapser(1).execute());
                Future<String> f3 = new HelloWorldHystrixCollapser(3).queue();
                Future<String> f4 = new HelloWorldHystrixCollapser(4).queue();

                Future<String> f5 = new HelloWorldHystrixCollapser(5).queue();
                Future<String> f6 = new HelloWorldHystrixCollapser(6).queue();
          
                System.out.println(System.currentTimeMillis() + " : " + f1.get());
                System.out.println(System.currentTimeMillis() + " : " + f2.get());
                System.out.println(System.currentTimeMillis() + " : " + f3.get());
                System.out.println(System.currentTimeMillis() + " : " + f4.get());
                System.out.println(System.currentTimeMillis() + " : " + f5.get());
                System.out.println(System.currentTimeMillis() + " : " + f6.get());
                // 下面3条都不在一个批量请求中
//                System.out.println(new HelloWorldHystrixCollapser(7).execute());
//                System.out.println(new HelloWorldHystrixCollapser(8).queue().get());
//                System.out.println(new HelloWorldHystrixCollapser(9).queue().get());

                // note：numExecuted表示共有几个命令执行，1个批量多命令请求算一个，这个实际值可能比代码写的要多，因为due to non-determinism of scheduler since this example uses the real timer
                int numExecuted = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size();
                System.out.println(System.currentTimeMillis() + " : " + "num executed: " + numExecuted);
                int numLogs = 0;
                for (HystrixInvokableInfo<?> command : HystrixRequestLog.getCurrentRequest().getAllExecutedCommands()) {
                    numLogs++;
                    System.out.println(System.currentTimeMillis() + " : " + command.getCommandKey().name() + " => command.getExecutionEvents(): " + command.getExecutionEvents());
                    assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
                }
                assertEquals(numExecuted, numLogs);
            } finally {
                context.shutdown();
            }
        }
}
