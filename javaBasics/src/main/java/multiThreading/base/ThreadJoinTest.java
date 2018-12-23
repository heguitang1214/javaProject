package multiThreading.base;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 *          等待某线程的终止
 */
public class ThreadJoinTest {
    public static void main(String[] args) throws Exception {
        ThreadJoin t = new ThreadJoin();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
        /*
        设置线程的优先级:
            Thread.MAX_PRIORITY:最高优先级(10)
            Thread.MIN_PRIORITY:最低优先级(1)
            Thread.NORM_PRIORITY:默认优先级(5)
         */
        t2.setPriority(Thread.MAX_PRIORITY);
        /*
            等待该线程终止
            main线程执行到t.join(),main线程就释放执行权和执行资格.等待t线程执行完毕,main线程再执行.
            如果t.join()前有线程t和t0,t和t0会相互争夺CPU执行权,但是main线程是否执行,只与t线程是否执行完毕有关,和t0无关.
            临时加入一个线程运算时可以使用join方法
         */
        t1.join();

        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "等待t1结束后才执行,结果为:" + i);
        }

    }
}


class ThreadJoin implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
//            System.out.println(Thread.currentThread().toString() + "线程输出结果:" + i);
            System.out.println(Thread.currentThread().getName() + "线程输出结果:" + i);
            Thread.yield();
        }

    }

}