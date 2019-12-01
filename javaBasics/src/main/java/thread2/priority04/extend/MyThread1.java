package thread2.priority04.extend;

/**
 * 线程优先级
 */
public class MyThread1 extends Thread {
    @Override
    public void run() {
        super.run();
        //输出线程级别
        System.out.println("MyThread1 Priority = " + this.getPriority());
        //启动线程MyThread2
        MyThread2 myThread2 = new MyThread2();
        myThread2.start();
    }
}
