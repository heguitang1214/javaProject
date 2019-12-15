package thread2.t02_interruption;

/**
 * 响应线程中断的方式: 重新中断
 */
public class ReInterrupt extends Thread {

    public static void main(String args[]) throws Exception {
        ReInterrupt reInterrupt = new ReInterrupt();
        System.out.println("Starting thread...");
        // 启动新线程(子线程)
        reInterrupt.start();
        // 主线程休眠3秒
        Thread.sleep(3000);
        System.out.println("Asking thread to stop...");
        // 设置子线程中断
        reInterrupt.interrupt();
        // 主线程休眠3秒
        Thread.sleep(3000);
        System.out.println("Stopping application...");
    }

    @Override
    public void run() {
        // for循环等待线程中断
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread running...");
            try {
                // 应该会执行3次
                // 线程阻塞，如果线程收到中断操作信号将抛出异常
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted...");
                // false
                System.out.println(this.isInterrupted());
                // 中不中断由自己决定，如果需要真的中断线程，则需要重新设置中断位
                // 如果不需要，则不用调用
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("reInterrupt..." + this.isInterrupted());
        System.out.println("Thread exiting...");
    }
}
