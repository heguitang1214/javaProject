package multiThreading.base.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 获取线程的返回值
 */
public class ThreadReturnValue {

    public static void main(String[] args) {

        /*
         * 1.获取Callable接口的返回值
         */
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(2000);
                        return "Hello";
                    }
                }
        );
        try {
            String info = future.get();//阻塞
            System.out.println("获取Callable接口的返回值为:" + info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 2.获取Runnable接口的返回值
         */
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future future1 = executorService1.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("runnable run()");
                    }
                }
        );
        try {
            Object o = future1.get();
            System.out.println("获取Runnable接口的返回值为:" + o);//null
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * 3.获取Runnable接口的返回值
         *      -指定返回值
         */
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        Future future2 = executorService2.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("runnable two run() ");
                    }
                }, 2
        );
        try {
            Object o = future2.get();
            System.out.println("获取Runnable接口的指定返回值为:" + o);//2
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * 4.通过CompletionService获取线程的返回值
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService =
                new ExecutorCompletionService<>(threadPool);
        for (int i = 0; i < 10; i++){
            final Integer seq = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));
                    return seq;
                }
            });
        }
        for (int i = 0; i < 10; i++){
            try {
                int result = completionService.take().get();
                System.out.println("通过CompletionService获取线程的返回值为:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
