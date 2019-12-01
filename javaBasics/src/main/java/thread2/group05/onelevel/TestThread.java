package thread2.group05.onelevel;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-16 20:31
 * @Description:
 */
public class TestThread implements Runnable {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("ThreadName = " + Thread.currentThread().getName());
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}