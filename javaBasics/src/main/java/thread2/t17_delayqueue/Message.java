package thread2.t17_delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 无双老师【云析学院:http://yunxiedu.net QQ:3190976240 email:zhouguanya20@163.com】
 * @Date: 2019-12-03 22:40
 * @Description: Message消息对象
 */
public class Message implements Delayed {
    /**
     * 延迟时间
     */
    private long time;

    /**
     * 名称
     */
    String name;

    public Message(String name, long time, TimeUnit unit) {
        this.name = name;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Message item = (Message) o;
        long diff = this.time - item.time;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return DelayQueueDemo.printDate() + "Message{" +
                "time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}
