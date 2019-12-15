package thread2.t07_synchronizedemo.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-22 19:53
 * @Description: 多个线程访问同一个对象的同一个方法
 *
 * 两个线程同时对一个对象的一个方法进行操作，只有一个线程能够抢到锁。
 * 因为一个对象只有一把锁，一个线程获取了该对象的锁之后，其他线程无法获取该对象的锁，
 * 就不能访问该对象的其他synchronized实例方法，但是可以访问非synchronized修饰的方法
 */
public class SynchronizedTest01 implements Runnable {
    /**
     * 共享资源
     */
    static int counter = 0;

    /**
     * synchronized 修饰实例方法
     */
    public synchronized void increase() {
        for (int j = 0; j < 10; j++) {
            System.out.println(Thread.currentThread().getName() + "执行累加操作。。。");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }
    }

    @Override
    public void run() {
        increase();
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest01 test = new SynchronizedTest01();
        Thread t1 = new Thread(test, "线程1");
        Thread t2 = new Thread(test, "线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
