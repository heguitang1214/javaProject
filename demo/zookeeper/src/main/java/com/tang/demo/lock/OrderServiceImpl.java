package com.tang.demo.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试分布式锁ImproveLock
 */
public class OrderServiceImpl implements Runnable {
    private static OrderCodeGenerator ong = new OrderCodeGenerator();

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    // 同时并发的线程数
    private static final int NUM = 60;
    // 按照线程数初始化倒计数器,倒计数器
    private static CountDownLatch cdl = new CountDownLatch(NUM);

    // private static Lock lock = new ReentrantLock();
    private Lock lock = new ImproveLock();

    // 创建订单接口
    public void createOrder() {
        String orderCode = null;

        lock.lock();
        try {
            // 获取订单编号
            orderCode = ong.getOrderCode();
            System.out.println("insert into DB使用id：=======================>" + orderCode);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }

        // ……业务代码，此处省略100行代码

        logger.info("insert into DB使用id：=======================>" + orderCode);
    }

    @Override
    public void run() {
        try {
            // 等待其他线程初始化
            cdl.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 创建订单
        createOrder();
    }

    public static void main(String[] args) {
        for (int i = 1; i <= NUM; i++) {
            // 按照线程数迭代实例化线程
            new Thread(new OrderServiceImpl()).start();
            // 创建一个线程，倒计数器减1
            cdl.countDown();
        }
    }
}
