package thread2.t11_aqs.sharelock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-08 10:14
 * @Description: 信号量——共享锁实现
 *
 * Semaphore还用于Hystrix限流框架中，控制系统并发在可控的范围内，保证系统高可用
 */
public class SemaphoreDemo {
    /**
     * 限定线程数量
     */
    private static final Semaphore SEMAPHORE = new Semaphore(3);
    /**
     * 每次获取的许可数
     */
    private static final int PERMITS = 2;

    /**
     * 线程
     */
    static class TestThread implements Runnable {
        @Override
        public void run() {
            try {
                // 获取许可
                SEMAPHORE.acquire(PERMITS);
                System.out.println(printCurrent() +
                        " : " + Thread.currentThread().getName() + " 进来了");
                // 持有许可，不会释放许可
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // ！！！！非常重要！！！！
                SEMAPHORE.release(PERMITS);
            }
        }

        /**
         * 打印时间
         *
         * @return  当前时间字符串
         */
        private String printCurrent() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sdf.format(new Date());
        }
    }

    public static void main(String[] args) {
        // 注意：本例只做演示使用。不要显示创建线程池，详情参考：Alibaba Java Coding Guidelines
        // 不要显式创建线程，请使用线程池。
        // 线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
        // 说明使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资源不足的问题。
        // 如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。
        Thread t1 = new Thread(new TestThread(), "TestThread1");
        Thread t2 = new Thread(new TestThread(), "TestThread2");
        Thread t3 = new Thread(new TestThread(), "TestThread3");
        Thread t4 = new Thread(new TestThread(), "TestThread4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
