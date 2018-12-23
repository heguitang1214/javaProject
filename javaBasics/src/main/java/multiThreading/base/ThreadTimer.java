package multiThreading.base;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 * 定时器
 */
public class ThreadTimer {

    public static void main(String[] args) {
//        timer1();
//        timer2();
        timer3();
    }

    //启动后5s后执行,每2s执行一次
    private static void timer1() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时器1....");
            }
        }, 3000, 2000);
    }

    //定时器的包含关系
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

    //定时器的包含关系
    private static void timer3() {
        //启动定时器,相当于两个参数5000,3000
        new Timer().schedule(new MyTimerTask(), 5000);
    }

    static class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("定时器3...");
            new Timer().schedule(new MyTimerTask(), 3000);
        }
    }



}
