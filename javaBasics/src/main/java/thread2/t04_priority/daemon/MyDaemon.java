package thread2.t04_priority.daemon;

/**
 * 守护线程
 */
public class MyDaemon implements Runnable {
    @Override
    public void run() {
        for (long i = 0; i < 9999999; i++) {
            System.out.println("守护线程第" + i + "次执行！");
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
