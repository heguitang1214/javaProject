package thread2.priority.daemon;

/**
 * 验证用户线程执行完后，守护线程未执行完即退出
 */
public class DaemonThreadDemo {
    public static void main(String[] args) {
        Thread t1 = new CommonThread();
        Thread t2 = new Thread(new MyDaemon());
        // 抛异常 ：IllegalThreadStateException
//        t2.start();
        //设置为守护线程
        t2.setDaemon(true);
        // 正确做法：线程运行前设置守护线程
        t2.start();
        t1.start();
    }
}
