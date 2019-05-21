package com.tang.hystrix.casetest.callapsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

/**
 * Hystrix请求合并
 *
 * @author : Five-云析学院
 * @since : 2019年04月15日 20:18
 */

public class HystrixCommand4Collapser extends HystrixCollapser<List<String>, String, Integer> {
    private final Integer key;

    public HystrixCommand4Collapser(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    /**
     * 创建一个批量请求命令
     * @param requests
     * @return
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> requests) {
        System.out.println("createCommand request size "+ requests.size());
        // 调用HystrixCommand实现类,构建Collapser命令
        return new BatchCommand(requests);
    }

    /**
     * 将批量请求的结果和对应的请求一一对应
     * @param batchResponse
     * @param requests
     */
    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> requests) {
        int count = 0;
        System.out.println("mapResponseToRequests request size "+ requests.size()+", and batchResponse size "+batchResponse.size());
        for (CollapsedRequest<String, Integer> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>>{

        private final Collection<CollapsedRequest<String, Integer>> requests;
        public BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BatchCommandGroupKey"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("BatchCommandKey"))
            );
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            ArrayList<String> responseList = new ArrayList<String>();
            // 处理每个请求，返回结果
            // System.out.println("request size "+ requests.size());
            for (CollapsedRequest<String, Integer> request : requests) {
                //模拟一个response
                String response = "ValueForKey: " + request.getArgument() + " thread:" + Thread.currentThread().getName();
                responseList.add(response);
            }
            System.out.println("responseListSize:"+responseList.size());
            return responseList;
        }
    }

    public static class UnitTest{
        @Test
        public void testHystrix4Collapser() throws Exception {

            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                Future<String> f1 = new HystrixCommand4Collapser(1).queue();
                Future<String> f2 = new HystrixCommand4Collapser(2).queue();
                Future<String> f3 = new HystrixCommand4Collapser(3).queue();
                TimeUnit.MILLISECONDS.sleep(10);
                Future<String> f4 = new HystrixCommand4Collapser(4).queue();

                System.out.println(System.currentTimeMillis() + " : " + f1.get());
                System.out.println(System.currentTimeMillis() + " : " + f2.get());
                System.out.println(System.currentTimeMillis() + " : " + f3.get());
                System.out.println(System.currentTimeMillis() + " : " + f4.get());
                // 下面3条都不在一个批量请求中
                System.out.println(new HystrixCommand4Collapser(5).execute());
                System.out.println(new HystrixCommand4Collapser(6).queue().get());
                System.out.println(new HystrixCommand4Collapser(7).queue().get());
            } finally {
                context.shutdown();
            }
        }
    }
}
