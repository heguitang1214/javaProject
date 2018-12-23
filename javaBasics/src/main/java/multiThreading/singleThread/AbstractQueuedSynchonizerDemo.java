package multiThreading.singleThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AbstractQueuedSynchonizerDemo {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        t1.start();
        t2.start();
    }


    static class MyThread extends Thread {
        private Lock lock;
        public MyThread(String name, Lock lock) {
            super(name);
            this.lock = lock;
        }

        public void run () {
            lock.lock();
            try {
                System.out.println(Thread.currentThread() + " running");
            } finally {
                lock.unlock();
            }
        }
    }

}
