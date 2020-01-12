package thread2.t15_cyclicbarrier;
import	java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-29 22:11
 * @Description: 旅行线程
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) throws Exception {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new TourGuideTask());
        // 推荐使用ThreadPoolExecutor创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            //登哥最大牌，到的最晚
            executor.execute(new TravelTask(cyclicBarrier, "哈登", 5));
            executor.execute(new TravelTask(cyclicBarrier, "保罗", 3));
            executor.execute(new TravelTask(cyclicBarrier, "戈登", 1));
        } finally {
            executor.shutdown();
        }
    }

    static String printDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sdf.format(new Date());
    }
}
