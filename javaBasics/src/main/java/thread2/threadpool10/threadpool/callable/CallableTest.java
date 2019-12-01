package thread2.threadpool10.threadpool.callable;

import java.util.concurrent.*;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-22 21:02
 * @Description: ForkJoin线程池使用  有返回值
 */
public class CallableTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = null;
        int count = 10;
        try {
            // !!! 不推荐使用Executors的静态方法创建线程池 !!!
            executor = Executors.newCachedThreadPool();
            CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
            for (int i = 0; i < count; i++) {
                FactorialCalculator factorialCalculator = new FactorialCalculator(i);
                completionService.submit(factorialCalculator);
            }

            for (int i = 0; i < count; i++) {
                Future<String> result = completionService.take();
                System.out.print(result.get());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            assert executor != null;
            executor.shutdown();
        }
    }
}
