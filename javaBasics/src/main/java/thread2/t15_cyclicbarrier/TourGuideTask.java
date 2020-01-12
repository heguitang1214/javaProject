package thread2.t15_cyclicbarrier;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-29 20:59
 * @Description: 导游线程，都到达目的地时，发放护照和签证
 */
public class TourGuideTask implements Runnable {

    @Override
    public void run() {
        System.out.println(CyclicBarrierDemo.printDate() + " " + "****导游分发护照签证****");
        try {
            //模拟发护照签证需要2秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
