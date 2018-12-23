package jvm.jdkTools;

import multiThreading.base.deadlock.Deadlock;

/**
 * Created by heguitang on 2018/11/4.
 * jstack的使用
 */
public class JstackTest {

    public static void main(String[] args) {
//        analysisDeadlock();
        deadCycle();
    }

    /**
     * 分析死锁
     */
    private static void analysisDeadlock() {
         /*
          线程任务的之执行需要具备两把锁,
          当一个线程一把锁的时候,就会出现死锁问题
         */
        Deadlock deadlock = new Deadlock(true);
        Deadlock deadlock1 = new Deadlock(false);
        new Thread(deadlock).start();
        new Thread(deadlock1).start();
    }

    /**
     * 分析死循环
     */
    private static void deadCycle() {
        int i = 0;
        while (true) {
//            System.out.println("当前第[" + (i++) + "]次循环......");
        }
    }


}
