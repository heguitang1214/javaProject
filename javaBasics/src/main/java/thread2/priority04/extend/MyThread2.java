package thread2.priority04.extend;

/**
 * 线程优先级
 */
public class MyThread2 extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("MyThread2 Priority = " + this.getPriority());
    }
}
