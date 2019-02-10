package thread.multiBase.threadCommunication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间通信：使用阻塞队列BlockingQueue实现
 * 用两个具有一个空间的队列来实现同步通知的功能
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {

        Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 50; i++) {
                    business.sub(i);
                }
            }
        }).start();

        for (int i = 1; i < 50; i++) {
            business.main(i);
        }
    }


    static class Business {
        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<>(1);

        //代码块（匿名构造代码块）
        {
            try {
                //初始化一个默认值
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //子任务
        void sub(int i) {
            try {
                queue1.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 1; j <= 10; j++) {
                System.out.println("子线程运行序号为" + j + ",当前第" + i + "次循环！");
            }
            try {
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //主任务
        public /*synchronized*/ void main(int i) {
            try {
                //默认queue2是满的，是不能放进去数据的,
                // 如果方法上面使用了synchronized，线程最开始运行main方法的话，直接就会死锁
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 1; j <= 20; j++) {
                System.out.println(">主线程运行序号为" + j + ",当前第" + i + "次循环！");
            }

            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}


