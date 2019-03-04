package thread.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  使一个线程等待其他线程完成各自的工作后再执行:
 *      -构造器中的计数值（count）实际上就是闭锁需要等待的线程数量
 *      -能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。
 *  使用一个计数器进行实现。计数器初始值为线程的数量。
 *  当每一个线程完成自己任务后，计数器的值就会减一。
 *  当计数器的值为0时，表示所有的线程都已经完成了任务，
 *  然后在CountDownLatch上等待的线程就可以恢复执行任务。
 *   测试同一时间的并发(发令枪)
 */
public class CountdownLatchTest {

    public static void main(String[] args) {
        countdownLatchTest();
//        concurrentTest();
    }

    /**
     * 测试CountdownLatchTest
     */
    private static void countdownLatchTest(){
        ExecutorService service = Executors.newCachedThreadPool();
        /*
         * 这里使用两个发令枪是因为需要拿到每个线程的结果
         */
        final CountDownLatch cdOrder = new CountDownLatch(1);//设置初始值为1
        final CountDownLatch cdAnswer = new CountDownLatch(3);//设置初始值为3
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "正准备接受命令");
                        //等待当前的计数器为0
                        cdOrder.await();
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "已接受命令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName() +
                                "回应命令处理结果");
                        //将cdAnswer计数器上的数值减少1
                        cdAnswer.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        //主线程
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("线程" + Thread.currentThread().getName() +
                    "即将发布命令");
            //将cdOrder计数器上的数值减少1
            cdOrder.countDown();
            System.out.println("线程" + Thread.currentThread().getName() +
                    "已发送命令，正在等待结果");
            //等待cdAnswer的计数器为0
            cdAnswer.await();
            System.out.println("线程" + Thread.currentThread().getName() +
                    "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
    }


    /**
     * 使用CountDownLatch测试并发
     */
    private static void concurrentTest(){
        CountDownLatch cdl = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cdl.await();//进行等待，等待一个指令，来唤醒所有的线程同步执行下面的代码
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("当前时间：" + System.currentTimeMillis());
//                    dmService.consumer();  //创建一个轮询，客户端
                }
            }).start();
            cdl.countDown(); //计数器，每调用1次减1，直到为0的时候，唤醒所有的等待在cdl上的线程
        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
