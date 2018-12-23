package rabbitMQ.javaDelayDemo;

import rabbitMQ.utils.CalendarUtils;

import java.util.concurrent.DelayQueue;

/**
 * 利用JDK的DelayQueue来实现延迟队列
 * 性能高，本机内存级别，适合单机
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        //创建一个队列用于存储订单任务
        DelayQueue<MyDelayed> delayQueue = new DelayQueue<>();

        //开启消费者轮询
        new Thread(new ConsumerDelay(delayQueue)).start();
        //生产者生产多个10秒的延时任务
        new Thread(new ProducerDelay(delayQueue, 10)).start();
    }

    /**
     * 延时任务生产者
     **/
    static class ProducerDelay implements Runnable {
        DelayQueue<MyDelayed> delayQueue;
        int delaySecond;//任务延迟时间

        ProducerDelay(DelayQueue<MyDelayed> delayQueue, int delaySecond) {
            this.delayQueue = delayQueue;
            this.delaySecond = delaySecond;
        }

        public void run() {
            String orderId = "订单ID:";
            for (int i = 0; i < 10; i++) {
                //定义一个Delay, 放入到DelayQueue队列中
                MyDelayed delay = new MyDelayed(this.delaySecond, orderId + i);
                //向队列中插入一个元素（延时任务）
                delayQueue.add(delay);   //元素插入之后，会根据剩余的时间来排序
                System.out.println(CalendarUtils.getCurrentTimeByStr(0) + " Thread " + Thread.currentThread() + " 添加了一个delay.orderId:" + orderId + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 延时任务消费者
     **/
    static class ConsumerDelay implements Runnable {

        DelayQueue<MyDelayed> delayQueue;

        public ConsumerDelay(DelayQueue<MyDelayed> delayQueue) {
            this.delayQueue = delayQueue;
        }

        public void run() {
            //轮询获取DelayQueue队列中当前超时的Delay元素
            while (true) {
                MyDelayed delayed = null;
                try {
                    //获取DelayQueue队列中的超时元素（Delay）
                    delayed = delayQueue.take();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //如果Delay元素存在,则任务到达超时时间
                if (delayed != null) {
                    //处理任务
                    System.out.println(CalendarUtils.getCurrentTimeByStr(0) + " Thread " + Thread.currentThread() + " 消费了一个delay. orderId:" + delayed.getOrderId());
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
