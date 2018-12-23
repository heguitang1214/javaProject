package multiThreading.singleThread;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest implements Runnable{

    private static ReentrantLock reentrantLock = new ReentrantLock(true);

    private static int number = 0;

    @Override
    public void run() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了锁！" + ++number);
//            Thread.sleep(1000 * 2);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }


    public static void main(String[] args) {
        LockTest lockTest = new LockTest();
        for (int i = 0; i < 1000; i++){
            Thread t1 = new Thread(lockTest, "线程" + i);
            t1.start();
        }
    }

}
