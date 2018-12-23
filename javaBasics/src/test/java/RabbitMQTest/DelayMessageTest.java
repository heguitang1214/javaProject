package RabbitMQTest;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rabbitMQ.DelayMessageApplication;
import rabbitMQ.utils.DelayMessageService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DelayMessageApplication.class)
public class DelayMessageTest {

    @Autowired
    @Qualifier("redisDelayMessageServiceImpl")
//	@Qualifier("mqDelayMessageServiceImpl")
    private DelayMessageService dmService;

    private final int THREAD_NUM = 10;
    private CountDownLatch cdl = new CountDownLatch(THREAD_NUM); //发令枪      栅栏

    //2.启动生产者
    @Test
    public void product() {
        dmService.product();
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //1.启动消费者
    @Test
    public void consumer() {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new DeleyThread()).start();
            cdl.countDown(); //  计数器   每调用1次， -1   直到为0 的时候，  唤醒所有的等待在cdl上的线程
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private class DeleyThread implements Runnable {
        public void run() {
            try {
                cdl.await();   //  等待    等待一个指令    唤醒所有的线程同步执行下面的代码
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dmService.consumer();  //创建一个轮询   客户端
        }
    }

}
