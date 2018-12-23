package rabbitMQ.javaDelayDemo;

import rabbitMQ.utils.CalendarUtils;

import java.util.Calendar;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 需要实现Delayed接口
 * 利用对象来实现
 */
public class MyDelayed implements Delayed {

    //任务超时时间戳
    private long expire = 0;
    private String orderId;

    public MyDelayed(int delaySecond, String orderId) {
        //任务超时时间戳 = 当前时间+延迟时间
        expire = CalendarUtils.getCurrentTimeInMillis(delaySecond);
        this.orderId = orderId;
    }

    /**
     * 需要实现的接口，获得延迟时间(过期时间-当前时间)     得到   任务还有多长时间超时！！！
     **/
    public long getDelay(TimeUnit unit) {
        Calendar cal = Calendar.getInstance();
        return expire - cal.getTimeInMillis();
    }

    /**
     * 用于延迟队列内部比较排序(当前时间的延迟时间 - 比较对象的延迟时间)      谁应该被放到队列的前面
     **/
    public int compareTo(Delayed o) {
        long d = (getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    public String getOrderId() {
        return orderId;
    }
}
