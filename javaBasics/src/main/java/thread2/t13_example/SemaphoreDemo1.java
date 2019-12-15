package thread2.t13_example;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.concurrent.Semaphore;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-22 20:19
 * @Description: 信号量的使用
 */
public class SemaphoreDemo1 {
    private final static int SEM_SIZE = 10;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(SEM_SIZE);
        MyThread thread1 = new MyThread("线程1", semaphore);
        MyThread thread2 = new MyThread("线程2", semaphore);
        thread1.start();
        thread2.start();
        int permits = 5;
        System.out.println(printDate() + Thread.currentThread().getName() + " trying to acquire");
        try {
            semaphore.acquire(permits);
            System.out.println(printDate() + Thread.currentThread().getName() + " acquire successfully");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(printDate() + Thread.currentThread().getName() + " release successfully");
        }
    }

    static class MyThread extends Thread {
        private Semaphore semaphore;

        public MyThread(String name, Semaphore semaphore) {
            super(name);
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            int count = 5;
            System.out.println(printDate() + Thread.currentThread().getName() + " trying to acquire");
            try {
                semaphore.acquire(count);
                System.out.println(printDate() + Thread.currentThread().getName() + " acquire successfully");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(count);
                System.out.println(printDate() + Thread.currentThread().getName() + " release successfully");
            }
        }
    }

    private static String printDate() {
        return sdf.format(new Date()) + " ";
    }
}
