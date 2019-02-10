package thread.multiBase;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 *          线程任务锁:执行的任务需要是线程安全的,不需要线程之间的通讯
 */
public class ThreadSynchronizedSafe {

    public static void main(String[] args) {
        new ThreadSynchronizedSafe().init();
    }

    private void init() {
        final Outputer outputer = new Outputer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("111111111111111111");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("qqqqqqqqqqqqqqqqqq");
                }
            }
        }).start();


    }

    //输出方法,线程安全
    class Outputer {

        //线程安全方法
        synchronized void output(String name) {
            for (int i = 0; i < name.length(); i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }

    }

}



