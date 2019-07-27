package thread2.priority.extend;

/**
 * 线程优先级的继承特性demo
 */
public class ThreadPriority {
    public static void main(String[] args) {
        System.out.println("once Main Thread Priority = " + Thread.currentThread().getPriority());
        //待会打开下面注释再看结果
//        Thread.currentThread().setPriority(10);
        System.out.println("twice Main Thread Priority = " + Thread.currentThread().getPriority());
        MyThread1 myThread1 = new MyThread1();
        myThread1.start();
    }
}
