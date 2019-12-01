package thread2.stop03;

/**
 * 线程中断停止线程
 */
public class InterruptThread extends Thread {
    /**
     * 退出标识
     */
    volatile boolean exit = false;

    @Override
    public void run() {

        while (!exit) {
            System.out.println(getName() + " is running");
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                System.out.println("week up from block...");
                // 在异常处理代码中修改共享变量的状态
                exit = true;
            }
        }
        System.out.println(getName() + " is exiting...");
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptThread interruptThread = new InterruptThread();
        System.out.println("Starting thread...");
        interruptThread.start();
        Thread.sleep(3000);
        System.out.println("Interrupt thread...: " + interruptThread.getName());
        // 设置退出标识为true 没有效果
//        interruptThread.exit = true;
        // todo 阻塞时退出阻塞状态
        interruptThread.interrupt();
        // 主线程休眠3秒以便观察线程interruptThread的中断情况
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }
}
