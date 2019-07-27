package thread2.priority.basemethod.yield;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-09
 * @Description: 生产者
 */
public class Producer extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("I am Producer : Produced Item " + i);
//            Thread.yield();
        }
    }
}
