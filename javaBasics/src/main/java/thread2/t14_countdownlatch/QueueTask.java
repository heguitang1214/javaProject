package thread2.t14_countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-24 19:48
 * @Description: 排队的任务
 */
public class QueueTask implements Runnable{

    private CountDownLatch countDownLatch;

    public QueueTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(CountDownLatchDemo.printDate() + " 开始排队，进入队列等待");
            Thread.sleep(10000);
            System.out.println(CountDownLatchDemo.printDate() + " 排队结束，可以开始交费");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}
