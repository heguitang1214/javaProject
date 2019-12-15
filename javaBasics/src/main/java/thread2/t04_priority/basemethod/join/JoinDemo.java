package thread2.t04_priority.basemethod.join;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-09
 * @Description:
 */
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException
    {
        Thread thread2
                = new Thread(() -> {
            System.out.println("thread2 started");
            System.out.println("thread2 sleeping for 2 seconds");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread2 completed");
        });

        Thread thread1 = new Thread(() -> {
            try {
                // 等待线程2执行完后再执行线程1
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("thread1 started");
            System.out.println("thread1 sleeping for 2 seconds");
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("thread1 completed");
        });


        // 线程1启动
        thread1.start();
        // 线程1调用join()
        // 问题：线程1和线程2的运行先后
        // 在主线程中调用thread1.join()，造成主线程阻塞，所以先执行完线程1，然后执行线程2
//        thread1.join();
        thread2.start();
    }
}
