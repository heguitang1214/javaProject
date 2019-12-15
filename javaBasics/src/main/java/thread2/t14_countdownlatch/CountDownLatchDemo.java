package thread2.t14_countdownlatch;
import	java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-09-24 19:48
 * @Description: 陪着媳妇去看病，轮到媳妇看大夫时，我就开始去排队准备交钱了。
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception{
        long now = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        // 建议使用ThreadPoolExecutor创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            executorService.execute(new SeeDoctorTask(countDownLatch));
            executorService.execute(new QueueTask(countDownLatch));
            // 等待其他线程完成各自的工作后再执
            countDownLatch.await();
            System.out.println(printDate() + " 搞定，回家！！！总共耗时:"+(System.currentTimeMillis()-now));
        } finally {
            executorService.shutdown();
        }
    }

    public static String printDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(new Date()) + " ";
    }
}
