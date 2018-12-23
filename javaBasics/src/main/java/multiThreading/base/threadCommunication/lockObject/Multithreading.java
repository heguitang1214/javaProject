package multiThreading.base.threadCommunication.lockObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 等待唤醒机制(生产者消费者模式)
 * 		多线程的生产消费模式
 */
public class Multithreading {
	public static void main(String[] args) {
		// 创建资源
		Resource resource = new Resource();
		//创建4个线程 两个生产者,两个消费者
		new Thread(new Producer(resource)).start();
		new Thread(new Producer(resource)).start();
		new Thread(new Consumer(resource)).start();
		new Thread(new Consumer(resource)).start();
	}

	static class Resource {
		private String name;
		private int count = 1;
		private boolean flag = false;
		//创建锁对象
		private Lock lock = new ReentrantLock();
		//通过已有的锁获取两组监视器,一组监视生产,一组监视消费者
		private Condition producer_con = lock.newCondition();
		private Condition consumer_con = lock.newCondition();

		/*
            资源写入方法:首先将该方法上锁,这样就实现了线程写入时的安全.
            然后,再使用producer_con监视生产者线程
         */
		public void set(String name) {
			//获取锁,获取锁后,可能会有异常出现,
			//而锁必须要释放,所以需要try{ }finally{ }
			lock.lock();
			try {
				while (flag) {
					try {
						//生产线程冻结
						producer_con.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				this.name = name + count;
				count++;
				System.out.println(Thread.currentThread().getName() + " 线程>生产<数据:" + this.name);
				flag = true;
				//唤醒消费线程中的任意一个
				consumer_con.signal();
			} finally {
				lock.unlock();//释放锁
			}
		}

		//消费数据
		public void out() {
			lock.lock();
			try {
				while (!flag) {
					try {
						consumer_con.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " 线程消费数据" + this.name);
				flag = false;
				producer_con.signal();
			} finally {
				lock.unlock();
			}
		}
	}

	//生产资源
	static class Producer implements Runnable {
		private Resource resource;

		Producer(Resource resource) {
			this.resource = resource;
		}

		public void run() {
			while (true) {
				resource.set("大盘鸡");
			}
		}
	}

	//消费资源
	static class Consumer implements Runnable {
		private Resource r;

		Consumer(Resource r) {
			this.r = r;
		}

		public void run() {
			while (true) {
				r.out();
			}
		}
	}
}


