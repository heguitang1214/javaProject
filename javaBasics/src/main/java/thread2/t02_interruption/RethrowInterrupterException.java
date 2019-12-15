package thread2.t02_interruption;

/**
 * 响应线程中断的方式：捕获异常并重新抛出
 */
public class RethrowInterrupterException {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = Thread.currentThread();
        try {
            thread.interrupt();
            System.out.println("能执行......设置了线程中断标志，sleep()表示可以中断！");
            Thread.sleep(3000);
            System.out.println("不能执行......");
        } catch (InterruptedException e) {
            System.out.println("做一些清理工作");
            throw e;
        }
    }

}
