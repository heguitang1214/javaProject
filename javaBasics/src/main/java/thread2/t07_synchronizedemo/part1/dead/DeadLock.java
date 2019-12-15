package thread2.t07_synchronizedemo.part1.dead;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-23 22:36
 * @Description: synchronized造成的死锁
 *
 * 任何一个Java对象都可以充当一把锁
 *
 * 分别定义"A" 和 "B"两把锁
 *
 * 启动两个线程t1 和 t2
 *
 * 不管t1或者t2谁先获取到CPU使用权都会死锁（start()方法使线程进入可运行状态——具体参考之前的视频）
 *
 * 1.假设t1优先获取CPU使用权，进入运行态。线程t1获取到A锁后休眠2s（休眠不会释放锁），然后尝试获取B锁
 *   随后t2获取CPU使用权，进入运行态。线程t2获取到B锁后休眠2s（休眠不会释放锁），然后尝试获取A锁
 *   这时候t1休眠结束，t1尝试获取B锁，等待t2释放B锁
 *   这时候t2休眠结束，t2尝试获取A锁，等待t1释放A锁
 *   t1和t2相互等待对方释放锁，此时造成死锁。
 *
 * 2.线程t2优先获取到CPU使用的情况与1类似。
 *
 */
public class DeadLock {

    /** A锁 */
    private static String A = "A";

    /** B锁 */
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLock().deadLock();
    }

    public void deadLock() {

        /**
         * 先获取A锁再获取B锁
         */
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                try {
                    // 获取A锁后休眠2s
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    // 获取B锁
                    System.out.println("thread1...");
                }
            }
        });

        /**
         * 先获取B锁再获取A锁
         */
        Thread t2 = new Thread(() -> {
            synchronized (B) {
                try {
                    // 获取B锁后休眠2s
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    System.out.println("thread2...");
                }
            }
        });

        t1.start();
        t2.start();

    }

}