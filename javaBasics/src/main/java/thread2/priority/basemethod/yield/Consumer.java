package thread2.priority.basemethod.yield;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-09
 * @Description: 消费者
 */
public class Consumer extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("I am Consumer : Consumed Item " + i);
//            Thread.yield();
        }
    }
}
