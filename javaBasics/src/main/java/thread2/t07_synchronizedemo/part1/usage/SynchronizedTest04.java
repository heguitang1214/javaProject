package thread2.t07_synchronizedemo.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-22 20:29
 * @Description: 当多个线程作用于不同的对象
 *
 * 因为两个线程作用于不同的对象，获得的是不同的锁，所以互相并不影响
 */
public class SynchronizedTest04 {
    /**
     * 同步方法1
     */
    public synchronized void method1() {
        System.out.println("Method 1 started：\t" + TimeUtils.currentTime());
        try {
            System.out.println("Method 1 execute：\t" + TimeUtils.currentTime());
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
            System.out.println("Method 2 execute：\t" + TimeUtils.currentTime());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 ended：\t" + TimeUtils.currentTime());
    }


    public static void main(String[] args) {

        new Thread(() -> new SynchronizedTest04().method1()).start();

        new Thread(() -> new SynchronizedTest04().method2()).start();
    }
}
