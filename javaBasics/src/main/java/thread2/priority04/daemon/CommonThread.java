package thread2.priority04.daemon;

/**
 * 普通用户线程
 */
public class CommonThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("用户线程第" + i + "次执行！");
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
