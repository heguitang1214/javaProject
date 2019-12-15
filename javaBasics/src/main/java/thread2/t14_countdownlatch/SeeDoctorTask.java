package thread2.t14_countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-24 19:47
 * @Description: 看大夫任务
 */
public class SeeDoctorTask implements Runnable{

    private CountDownLatch countDownLatch;

    public SeeDoctorTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(CountDownLatchDemo.printDate() + " 开始看大夫，大夫开始诊脉");
            Thread.sleep(5000);
            System.out.println(CountDownLatchDemo.printDate() + " 看大夫结束，大夫开药方子");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}
