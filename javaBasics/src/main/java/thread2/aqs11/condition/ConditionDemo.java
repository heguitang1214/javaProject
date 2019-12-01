package thread2.aqs11.condition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-02 21:32
 * @Description: Condition使用
 */
public class ConditionDemo {
    /**
     * 获取独占锁
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * 获取条件1
     */
    private Condition condition1 = lock.newCondition();
    /**
     * 获取条件2
     */
    private Condition condition2 = lock.newCondition();
    /**
     * 获取条件3
     */
    private Condition condition3 = lock.newCondition();
    /**
     * 日期格式化
     */
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo conditionDemo = new ConditionDemo();
        // Alibaba Java Coding Guidelines plugin 插件提示不要直接使用Executors
        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread1 thread1 = conditionDemo.new Thread1();
        Thread2 thread2 = conditionDemo.new Thread2();
        Thread3 thread3 = conditionDemo.new Thread3();
        // 启动线程任务1、2、3.
        executorService.execute(thread1);
        executorService.execute(thread2);
        executorService.execute(thread3);
        Thread.sleep(2000);
        // 依次唤醒各线程任务.
        signalTest(conditionDemo);
        executorService.shutdown();
    }

    /**
     * 依次唤醒各线程任务,以观察condition的作用
     *
     * @param conditionDemo ConditionDemo对象
     * @throws InterruptedException 中断异常
     */
    public static void signalTest(ConditionDemo conditionDemo) throws InterruptedException {
        // 获取独占锁 唤醒condition1的线程
        conditionDemo.lock.lock();
        conditionDemo.condition1.signal();
        // 释放独占锁 等待thread1执行完毕.
        conditionDemo.lock.unlock();
        Thread.sleep(2000);

        // 获取独占锁 唤醒condition2的线程
        conditionDemo.lock.lock();
        conditionDemo.condition2.signal();
        // 释放独占锁 等待thread2执行完毕.
        conditionDemo.lock.unlock();
        Thread.sleep(2000);

        // 获取独占锁 唤醒condition3的线程
        conditionDemo.lock.lock();
        conditionDemo.condition3.signal();
        // 释放独占锁 等待thread2执行完毕.
        conditionDemo.lock.unlock();
        Thread.sleep(2000);
    }

    /**
     * 线程任务定义类
     */
    public class Thread1 implements Runnable {

        public Thread1() {

        }

        @Override
        public void run() {
            try {
                // 设置线程名称
                Thread.currentThread().setName(Thread1.class.getSimpleName());
                System.out.printf("%s线程启动\n", Thread.currentThread().getName());
                lock.lock();
                // 在condition1上阻塞，并且释放独占锁lock.
                condition1.await();
                System.out.printf("%s线程被唤醒", Thread.currentThread().getName());
                printDate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 线程任务定义类
     */
    public class Thread2 implements Runnable {
        public Thread2() {

        }

        @Override
        public void run() {
            try {
                Thread.currentThread().setName(Thread2.class.getSimpleName());
                System.out.printf("%s线程启动\n", Thread.currentThread().getName());
                lock.lock();
                // 在condition2上阻塞，并且释放独占锁lock.
                condition2.await();
                System.out.printf("%s线程被唤醒", Thread.currentThread().getName());
                printDate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 线程任务定义类
     */
    public class Thread3 implements Runnable {
        public Thread3() {

        }

        @Override
        public void run() {
            try {
                Thread.currentThread().setName(Thread3.class.getSimpleName());
                System.out.printf("%s线程启动\n", Thread.currentThread().getName());
                lock.lock();
                // 在condition2上阻塞，并且释放独占锁lock.
                condition3.await();
                System.out.printf("%s线程被唤醒", Thread.currentThread().getName());
                printDate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 打印时间
     */
    void printDate() {
        System.out.println("【当前时间：" + simpleDateFormat.format(new Date()) + "】");
    }
}
