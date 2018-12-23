package multiThreading.base.deadlock;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 *  死锁测试
 */
public class DeadlockTest {

    public static void main(String[] args) {
        /*
          线程任务的之执行需要具备两把锁,
          当一个线程一把锁的时候,就会出现死锁问题
         */
        Deadlock deadlock = new Deadlock(true);
        Deadlock deadlock1 = new Deadlock(false);
        new Thread(deadlock).start();
        new Thread(deadlock1).start();
    }

}
