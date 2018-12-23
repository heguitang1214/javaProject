package multiThreading.demo.syncTool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  信号灯
 *      -Semaphore是计数信号量。Semaphore管理一系列许可证。
 *      每个acquire方法阻塞，直到有一个许可证可以获得然后拿走一个许可证；
 *      -每个release方法增加一个许可证，这可能会释放一个阻塞的acquire方法。
 *      然而，其实并没有实际的许可证这个对象，Semaphore只是维持了一个可获得许可证的数量。
 *      Semaphore经常用于限制获取某种资源的线程数量。
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        sp.acquire();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "进入，当前已有" + (3 - sp.availablePermits()) + "个并发");
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "即将离开");
                    sp.release();
                    //下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "已离开，当前已有" + (3 - sp.availablePermits()) + "个并发");
                }
            };
            service.execute(runnable);
        }
    }

}
