package thread2.synchronizedemo07.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-22 20:19
 * @Description: 一个线程获取了该对象的锁之后，其他线程来访问其他非synchronized实例方法现象
 *
 * 当线程1还在执行时，线程2也执行了，所以当其他线程来访问非synchronized修饰的方法时是可以访问的
 */
public class SynchronizedTest03 {
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
    public void method2() {
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
        final SynchronizedTest03 test = new SynchronizedTest03();

        new Thread(() -> test.method1()).start();

        new Thread(() -> test.method2()).start();
    }
}
