package thread.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        readWriteLockTest();
    }


    /**
     * 读写锁的使用
     */
    private static void readWriteLockTest(){
        Queue queue = new Queue();
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
    }


    class CachedData{
        private Map<String, Object> cache = new HashMap<>();

        /**
         * 该锁上可以挂了<写锁>后，再挂<读锁>
         *     rw上先挂了写锁，然后再挂读锁，会导致写锁降级，降级为更新锁，也就是读写锁
         */
        private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        /**
         * 读写锁设计缓存系统：读数据的时候，都会先挂上自己的读锁，但是不会造成阻塞，
         *                  当发现没有数据的时候，就会挂写锁，进行排他。
         *                  写完数据后再挂读锁，读取数据。
         * @param key key值
         * @return 返回的数据
         */
        private Object getData(String key) {
            rw.readLock().lock();
            Object value;//获取缓存的数据
            try {
                value = cache.get(key);//获取缓存的数据
                if (value == null) {
                    rw.readLock().unlock();
                    //可能三个线程同时获取写锁，分别是a，b，c，但是只有一个线程能获取成功，假设为a获取成功
                    rw.writeLock().lock();
                    try {
                        //防止a线程在写完数据后，b，c又来修改该数据，所以要再次判断。
                        if (value == null) {
                            value = "aaa";//查询数据库,获取数据，写入到缓存
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

        //模拟查询数据
        private Object getValue(String key){
            System.out.println(key);
            int num = new Random().nextInt(10);
            if (num % 2 == 0){
                return new Object();
            }else {
                return null;
            }
        }
    }

}


