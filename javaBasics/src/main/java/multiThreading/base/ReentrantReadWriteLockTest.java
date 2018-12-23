package multiThreading.base;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {

//        HashSet<Integer> hashSet = new HashSet<>();
//        hashSet.add(1);

        final Queue queue = new Queue();
        for (int i = 0; i < 3; i++) {
//            读数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        queue.get();
                    }
                }
            }).start();

//            写数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        queue.put(new Random().nextInt(10000));
                    }
                }
            }).start();
        }
    }

    static class Queue {
        //共享数据:只能有一个线程能写数据,但可以有多个线程同时读数据
        private Object data = null;
        private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

        public void get() {
            rw.readLock().lock();//读锁
            try {
                System.out.println(Thread.currentThread().getName() + "开始读数据...");
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println(Thread.currentThread().getName() + "读取数据完成,获得数据为:" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rw.readLock().unlock();
            }
        }

        public void put(Object data) {
            rw.writeLock().lock();//写锁
            try {
                System.out.println(Thread.currentThread().getName() + "开始写数据...");
                Thread.sleep((long) (Math.random() * 10000));
                this.data = data;
                System.out.println(Thread.currentThread().getName() + "写数据完成,写入的数据为>>>" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rw.writeLock().unlock();
            }
        }

        //读写锁设计缓存系统
        private Object getData(String key) {
            rw.readLock().lock();
            Object value = null;
            try {
                value = null;//获取缓存的数据
                if (value == null) {
                    rw.readLock().unlock();
                    rw.writeLock().lock();
                    try {
                        if (value == null) {
                            value = "aaa";//查询数据库,写入到缓存
                        }
                    } finally {
                        rw.writeLock().unlock();
                    }
                    rw.readLock().lock();
                }
            } finally {
                rw.readLock().unlock();
            }
            return value;
        }


    }
}


