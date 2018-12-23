package multiThreading.base.threadCommunication.sync;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 * 等待唤醒机制:单线程通信,一个生产者,一个消费者
 * 		更换成多线程之间的通讯,就需要使用线程的notifyAll()
 */
public class SingleThread {
	public static void main(String[] args) {
		// 创建资源
		Resource resource = new Resource();
		new Thread(new InputData(resource)).start();
		new Thread(new OutputData(resource)).start();
	}

	// 资源
	static class Resource {
		private String name;
		private String sex;
		private boolean flag = false;
		//资源写入
		public synchronized void set(String name, String sex) {
			//flag为真,资源里面有内容,冻结r锁中的输入线程
			if (flag) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.name = name;
			this.sex = sex;
			System.out.println(Thread.currentThread().getName() + "_资源写入完毕:name=" + name + ",sex=" + sex);
			//写入资源完毕,状态标记为true
			flag = true;
			//唤醒r锁中的输出线程
			this.notify();
		}
		//资源写出
		public synchronized void out() {
			//有资源falsg为true,读取资源,!flag为false,不执行该方法
			//没有资源falsg为false,!flag为true,输出线程沉睡
			if (!flag) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + ">>获取数据:name=" + name + ",性别:" + sex);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			flag = false;
			this.notify();
		}
	}

	//线程输入
	static class InputData implements Runnable {
		private Resource resource;
		InputData(Resource resource) {
			this.resource = resource;
		}
		public void run() {
			int x = 0;
			while (true) {
				if (x == 0) {
					resource.set("小明", "男");
				} else {
					resource.set("小美", "女");
				}
				x = (x + 1) % 2;
			}
		}
	}

	//线程输出
	static class OutputData implements Runnable {
		private Resource resource;
		OutputData(Resource resource) {
			this.resource = resource;
		}
		public void run() {
			while (true) {
				resource.out();
			}
		}
	}

}

