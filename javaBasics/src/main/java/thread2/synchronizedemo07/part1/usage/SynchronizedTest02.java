package thread2.synchronizedemo07.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-22 19:59
 * @Description: 一个线程获取了该对象的锁之后，其他线程来访问其他synchronized实例方法现象
 *
 * 可以看出其他线程来访问synchronized修饰的其他方法时需要等待线程1先把锁释放
 */
public class SynchronizedTest02 {
    /**
     * 同步方法1
     */
    public synchronized void method1() {
        System.out.println("Method 1 started：\t" + TimeUtils.currentTime());
        try {
            System.out.println("Method 1 executed：\t" + TimeUtils.currentTime());
            // sleep方法不会释放监视锁
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 ended：\t" + TimeUtils.currentTime());
    }

    /**
     * 同步方法2
     */
    public synchronized void method2() {
        System.out.println("Method 2 started：\t" + TimeUtils.currentTime());
        try {
            System.out.println("Method 2 executed：\t" + TimeUtils.currentTime());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 ended：\t" + TimeUtils.currentTime());
    }


    public static void main(String[] args) {
        final SynchronizedTest02 test = new SynchronizedTest02();

        new Thread(() -> test.method1()).start();

        new Thread(() -> test.method2()).start();
    }
}
