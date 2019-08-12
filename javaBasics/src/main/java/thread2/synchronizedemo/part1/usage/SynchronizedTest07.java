package thread2.synchronizedemo.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-23 00:07
 * @Description:
 */
public class SynchronizedTest07 implements Runnable {

    static int counter = 0;

    @Override
    public void run() {
        //省略其他耗时操作....
        //使用同步代码块对变量i进行同步操作,锁对象为instance
        synchronized (SynchronizedTest07.class) {
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
    }


    public static void main(String[] args) throws InterruptedException {
        final SynchronizedTest07 test = new SynchronizedTest07();
        Thread t1 = new Thread(test, "线程1");
        Thread t2 = new Thread(test,"线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
