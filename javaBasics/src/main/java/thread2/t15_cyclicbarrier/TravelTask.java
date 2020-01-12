package thread2.t15_cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-29 20:19
 * @Description: 旅行线程
 */
public class TravelTask implements Runnable {
    /**
     * 栅栏
     */
    private CyclicBarrier cyclicBarrier;
    /**
     * 姓名
     */
    private String name;
    /**
     * 赶到的时间
     */
    private int arriveTime;

    public TravelTask(CyclicBarrier cyclicBarrier, String name, int arriveTime) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
        this.arriveTime = arriveTime;
    }

    @Override
    public void run() {
        try {
            //模拟达到需要花的时间
            Thread.sleep(arriveTime * 1000);
            System.out.println(CyclicBarrierDemo.printDate() + " " + name + "到达集合点");
            cyclicBarrier.await();
            System.out.println(CyclicBarrierDemo.printDate() + " " + name + "开始旅行啦～～");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
