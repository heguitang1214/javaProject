package thread.executorDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by heguitang on 2019/2/1.
 * 线程池
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
//        固定线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//        缓存线程池
        ExecutorService threadPool1 = Executors.newCachedThreadPool();
//        单个线程池，始终会有一个线程：如果线程池中的线程死了，就会创建一个新的线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        for (int i = 1; i < 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 1; j < 10; j++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() +
                                " is looping of[" + j + "]for task of[" + task + "]");
                    }
                }
            });
        }
        //停止线程池
        threadPool.shutdown();
    }

}
