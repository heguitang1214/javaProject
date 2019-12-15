package thread2.t05_group.batch;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-16 21:22
 * @Description:
 */
public class ThreadGroupBatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int batch = 5;
        ThreadGroup threadGroup = new ThreadGroup("我的线程组");
        for (int i = 0; i < batch; i++) {
            BatchThread batchThread
                    = new BatchThread(threadGroup, "线程" + i);
            batchThread.start();
        }
        Thread.sleep(10000);
        threadGroup.interrupt();
        System.out.println("调用了ThreadGroup.interrupt()方法");
    }
}
