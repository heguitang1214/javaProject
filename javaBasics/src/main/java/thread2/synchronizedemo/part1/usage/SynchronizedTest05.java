package thread2.synchronizedemo.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-22 23:38
 * @Description: synchronized作用于静态方法
 *
 * 两个线程实例化两个不同的对象，但是访问的方法是静态的，两个线程发生了互斥（即一个线程访问，另一个线程只能等着），
 * 因为静态方法是依附于类而不是对象的，当synchronized修饰静态方法时，锁是class对象。
 */
public class SynchronizedTest05 implements Runnable {
    /**
     * 共享资源
     */
    static int counter = 0;

    /**
     * synchronized 修饰静态方法方法
     */
    public static synchronized void increase() {
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
        Thread t1 = new Thread(new SynchronizedTest05(), "线程1");
        Thread t2 = new Thread(new SynchronizedTest05(), "线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
