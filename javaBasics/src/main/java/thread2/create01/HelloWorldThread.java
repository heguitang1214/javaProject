package thread2.create01;

/**
 * 继承Thread类创建线程
 *
 * @author tang
 */
public class HelloWorldThread extends Thread {

    /**
     * 重写run()方法，run()方法的方法体就是线程的执行体
     */
    @Override
    public void run() {
        System.out.println("Hello Concurrent World");
    }

    public static void main(String[] args) {
        System.out.println("--------创建线程--------");
        HelloWorldThread helloWorldThread = new HelloWorldThread();
        System.out.println("--------启动线程--------");
        helloWorldThread.start();
    }
}
