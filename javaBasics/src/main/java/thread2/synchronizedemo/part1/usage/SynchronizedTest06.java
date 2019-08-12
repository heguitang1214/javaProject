package thread2.synchronizedemo.part1.usage;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-07-22 23:57
 * @Description: synchronized作用于同步代码块
 *
 * 为什么要同步代码块呢？
 * 在某些情况下，我们编写的方法体可能比较大，同时存在一些比较耗时的操作，
 * 而需要同步的代码只有一小部分，如果直接对整个方法进行同步操作，可能会得不偿失，
 * 此时我们可以使用同步代码块的方式对需要同步的代码进行包裹，这样就无需对整个方法进行同步操作了。
 * 将synchronized作用于一个给定的实例对象instance，即当前实例对象就是锁对象，
 * 每次当线程进入synchronized包裹的代码块时就会要求当前线程持有instance实例对象锁，
 * 如果当前有其他线程正持有该对象锁，那么新到的线程就必须等待，这样也就保证了每次只有一个线程执行i++;操作。
 * 当然除了instance作为对象外，我们还可以使用this对象(代表当前实例)或者当前类的class对象作为锁，如下代码：
 */
public class SynchronizedTest06 implements Runnable {

    static SynchronizedTest06 instance = new SynchronizedTest06();

    static int counter = 0;

    @Override
    public void run() {
        //省略其他耗时操作....
        //使用同步代码块对变量i进行同步操作,锁对象为instance
        synchronized (instance) {
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
        Thread t1 = new Thread(instance, "线程1");
        Thread t2 = new Thread(instance, "线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
