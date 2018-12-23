package jvm.jdkTools;

import multiThreading.base.deadlock.Deadlock;

/**
 * Created by 11256 on 2018/9/12.
 * jstack分析死锁
 */
public class AnalysisDeadlock {

    public static void main(String[] args) {
        /*
          线程任务的之执行需要具备两把锁,
          当一个线程一把锁的时候,就会出现死锁问题
         */
        Deadlock deadlock = new Deadlock(true);
        Deadlock deadlock1 = new Deadlock(false);
        new Thread(deadlock).start();
        new Thread(deadlock1).start();
    }

}
