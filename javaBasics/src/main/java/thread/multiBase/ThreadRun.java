package thread.multiBase;

/**
 * Created by heguitang on 2019/1/31.
 * 线程运行的任务
 */
public class ThreadRun {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runable 的run()....");
            }
        }) {
            @Override
            public void run() {
                System.out.println("Thread 的run()....");
            }
        };

        thread.start();
//        Thread 的run()....
    }

}
