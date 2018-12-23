package multiThreading.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 */
public class FairLockDemo implements Runnable {
    //公平锁
//    private static ReentrantLock lock = new ReentrantLock(true);
    //非公平锁
    private static ReentrantLock lock = new ReentrantLock(false);

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了锁! ");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        FairLockDemo test = new FairLockDemo();
        //创建线程
        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(test, "线程" + i);
            t1.start();
        }
    }
}
