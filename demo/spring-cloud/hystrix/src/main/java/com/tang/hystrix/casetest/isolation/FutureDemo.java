package com.tang.hystrix.casetest.isolation;

import java.util.Random;
import java.util.concurrent.*;

/**
 * TODO
 *
 * @author:Five-云析学院
 * @since:2019年05月08日 21:30
 */
public class FutureDemo {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Random random = new Random();
        Future<?> future = service.submit(new Runnable() {
            @Override
            public void run() {
                int val = random.nextInt(1000);
                try {
                    TimeUnit.MILLISECONDS.sleep(val);

                    System.out.println("random value is " + val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            future.get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println("超时了!!!!!!");
            FutureDemo.fallback();
            e.printStackTrace();
        }
        service.shutdown();
    }
    public static String fallback(){
        return "fallback";
    }
}
