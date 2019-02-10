package thread.multiBase;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 * 定时器
 */
public class ThreadTimer {

    private static int count = 0;

    public static void main(String[] args) {
//        timer1();
//        timer2();
        timer3();
//        timer4();

        while (true){
            System.out.println(LocalDateTime.now().getSecond());
//            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 启动后5s后执行,每2s执行一次
     */
    private static void timer1() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时器1....");
            }
        }, 5000, 2000);
    }


    /**
     * 定时器的包含关系
     *
     */
    private static void timer2() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//                System.out.println("定时器2....");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("定时器2...");
                    }
                }, 2000);
            }
        }, 2000);
    }

    /**
     * 定时器的包含关系
     *  5s后启动，定时执行任务MyTimerTask()
     */
    private static void timer3() {
        //启动定时器,相当于两个参数5000,3000
        new Timer().schedule(new MyTimerTask(), 5000);
    }


    /**
     * 定时器的包含关系
     *  实现定时器的交替执行。2s、4s的交替执行，实现交替靠的是MyTimerTask1中的实现
     */
    private static void timer4() {
        //这里的new MyTimerTask1()为创建一个新的定时器，因为前一个已经使用了，只能重新创建。
        new Timer().schedule(new MyTimerTask1(), 2000);
    }



    static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("定时器3...");
            new Timer().schedule(new MyTimerTask(), 3000);
        }
    }

    static class MyTimerTask1 extends TimerTask {
        @Override
        public void run() {
            count = (count + 1) % 2;
            System.out.println("定时器4...");
            new Timer().schedule(new MyTimerTask1(), 2000 + 2000 * count);
        }
    }

}
