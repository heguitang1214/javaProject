package thread2.group05.batch;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-16 21:21
 * @Description:
 */
public class BatchThread extends Thread {
    public BatchThread(ThreadGroup tg, String name) {
        super(tg, name);
    }

    @Override
    public void run() {
        System.out.println("ThreadName = " + Thread.currentThread().getName() +
                "开始死循环了");
        while (!this.isInterrupted()) {

        }
        System.out.println("ThreadName = " + Thread.currentThread().getName() +
                "结束了");
    }
}
