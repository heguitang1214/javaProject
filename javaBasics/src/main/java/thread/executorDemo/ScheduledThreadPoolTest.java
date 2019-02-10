package thread.executorDemo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by heguitang on 2019/2/1.
 * 线程池的定时任务
 *      没有直接使用绝对时间的方法，可以间接使用
 *      schedule(task, date.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
 */
public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
//        test1();
        test2();

    }

    /**
     * 10s后启动
     */
    private static void test1(){
        Executors.newScheduledThreadPool(3).schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("定时任务...");
            }
        }, 10, TimeUnit.SECONDS);
    }

    /**
     * 6s后启动，每隔2s运行一次
     */
    private static void test2(){
        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("定时任务...");
            }
        }, 6, 2, TimeUnit.SECONDS);
    }

}
