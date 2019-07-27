package thread2.priority.basemethod.sleep;


/**
 * 验证sleep()方法不会释放锁、wait()方法会释放锁
 */
public class SleepTest {

    /**
     * sleep()方法不会释放锁，因此线程是按照先后顺序执行的
     */
    public synchronized void sleepMethod() {
        System.out.println("Sleep start ： "
                + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sleep end ： "
                + Thread.currentThread().getName());
    }

    /**
     * wait()方法会释放锁，因此一旦调用wait()方法就会造成其他线程运行
     */
    public synchronized void waitMethod() {
        System.out.println("Wait start ： "
                + Thread.currentThread().getName());
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Wait end ："
                + Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        final SleepTest test1 = new SleepTest();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> test1.sleepMethod()).start();
        }


        try {
            //暂停十秒，等上面程序执行完成
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-----分割线-----");

        final SleepTest test2 = new SleepTest();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> test2.waitMethod()).start();
        }

    }
}
