package multiThreading.demo.exercises;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 现有的程序代码模拟产生了16个日志对象，并且需要运行16秒才能打印完这些日志，
 * 程序中增加4个线程去调用parseLog()方法来分头打印这16个日志对象，
 * 程序只需要运行4秒即可打印完这些日志对象。原始代码如下：
 */
public class PrintSysLog {

    public static void main(String[] args) {

        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(16);
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String log = queue.take();
                            parseLog(log);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }


        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        /*模拟处理16行日志，下面的代码产生了16个日志对象，当前代码需要运行16秒才能打印完这些日志。
        修改程序代码，开四个线程让这16个对象在4秒钟打完。
        */
        for (int i = 0; i < 16; i++) {  //这行代码不能改动
            final String log = "" + (i + 1);//这行代码不能改动
            {
//                PrintSysLog.parseLog(log);
                try {
                    queue.put(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //打印日志的方法不能修改
    private static void parseLog(String log) {
        System.out.println(log + "当前日志信息:" + (System.currentTimeMillis() / 1000));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}