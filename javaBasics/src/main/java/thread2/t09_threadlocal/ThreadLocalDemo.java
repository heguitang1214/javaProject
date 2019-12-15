package thread2.t09_threadlocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-16 19:50
 * @Description: ThreadLocal使用方式
 * <p>
 * 从运行结果可以看出，每个线程第一次调用TheadLocal对象的get方法时都得到初始值3，注意我们上面的代码是让三个线程顺序执行
 * 显然从运行结果看，第一个线程结束后设置的新值，对第二个线程是没有影响的
 * 第二个线程完成后设置的新值对第三个线程也没有影响。
 * 这就仿佛把ThreadLocal对象当做每个线程内部的对象一样，但实际上threadLocal对象是个外部类对象，
 * 内部类Worker访问到的是同一个threadLocal对象，也就是说是被各个线程共享的。这是如何做到的呢？我们就来看看ThreadLocal对象的内部原理。
 */
public class ThreadLocalDemo {
    /**
     * 定义了一个ThreadLocal<Integer>对象，
     * 并重写它的initialValue方法，初始值是3
     * 这个对象会在三个线程间共享
     */
    private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 3);
    /**
     * 设置一个信号量，许可数为1，让三个线程顺序执行
     */
    private Semaphore semaphore = new Semaphore(1);
    /**
     * 一个随机数
     */
    private Random random = new Random();

    /**
     * 每个线程中调用这个对象的get方法，再调用一个set方法设置一个随机值
     */
    public class Worker implements Runnable {
        @Override
        public void run() {

            try {
                // 随机延时1s以内的时间
                Thread.sleep(random.nextInt(1000));
                // 获取许可
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 从threadLocal中获取值
            int value = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + " threadLocal old value : " + value);
            // 修改value值
            value = random.nextInt();
            // 新的value值放入threadLocal中
            threadLocal.set(value);
            System.out.println(Thread.currentThread().getName() + " threadLocal new value: " + value);
            System.out.println(Thread.currentThread().getName() + " threadLocal latest value : " + threadLocal.get());
            // 释放信号量
            semaphore.release();
            // 在线程池中,当线程退出之前一定要记得调用remove方法，因为在线程池中的线程对象是循环使用的
            threadLocal.remove();
        }
    }

    /**
     * 创建三个线程，每个线程都会对ThreadLocal对象进行操作
     */
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        es.execute(threadLocalDemo.new Worker());
        es.execute(threadLocalDemo.new Worker());
        es.execute(threadLocalDemo.new Worker());
        es.shutdown();
    }
}
