package multiThreading.singleThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitAndNotifyDemo {

    private static final Logger logger = LoggerFactory.getLogger(WaitAndNotifyDemo.class);

    public static void main(String[] args) throws InterruptedException {
//        wait1();
        wait2();
    }

    /**
     * 线程
     */
    private static void wait1(){
        MyThread myThread = new MyThread();
        synchronized (myThread) {
            try {
                //这个线程不能立即运行，是因为run()中使用了synchronized，
                //并且锁对象没有释放，被主线程持有
                myThread.start();
                // 主线程睡眠3s
                Thread.sleep(2000);
                System.out.println("before wait");
                // 阻塞主线程，因为是同一把锁，才能阻塞主线程，
                //如果不是同一把锁，主线程就直接走完了，myThread再调用wait()
                //就是失败，wait()会释放锁
                myThread.wait();
                System.out.println("after wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 线程先调用nodify，然后再调用wait，主线程得不到唤醒，一直沉睡。
     */
    private static void wait2() throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        // 主线程睡眠3s
        Thread.sleep(3000);
        synchronized (myThread) {
            try {
                System.out.println("before wait");
                // 阻塞主线程，因为主线程使用的锁是myThread，
                // 所以同步代码块中，可以使用myThread来阻塞主线程
                myThread.wait();
                System.out.println("after wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class MyThread extends Thread {
        public void run() {
            synchronized (this) {
                System.out.println("before notify");
                notify();
                System.out.println("after notify");
            }
        }
    }
}