package multiThreading.base;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/13]
 *  停止线程
 *      主线程结束,其他线程也要结束
 */
public class StopThreadTest {
    public static void main(String[] args) {
        StopThread stopThread = new StopThread();
        Thread t1 = new Thread(stopThread);
        Thread t2 = new Thread(stopThread);
        t1.start();
        t2.setDaemon(true);//守护线程
        t2.start();

        int num = 1;
        for (; ; ) {
            //停止线程
            if (++num == 50) {
//                stopThread.setFlag();//修改标记
                /*
                    将线程从冻结状态强制恢复到运行状态,让线程具备CPU的执行资格
                    在主线程中对其冻结的线程进行interupt()强制唤醒(该线程已经沉睡,无法判断标记,该方法会抛出异常),
                    在异常处理里面改变其标记,则可在主线程结束的时候使其(冻结的线程)线程也结束
                 */
                t1.interrupt();
//                t2.interrupt();
                break;
            }
            System.out.println(Thread.currentThread().getName() + "线程数据为:" + num);
        }
        System.out.println(Thread.currentThread().getName() + "线程结束");
    }


}


/**
 * run方法控制线程的结束
 *      任务中都会有循环结构,只要控制住循环就可以结束任务,
 *      控制循环通常用定义标记(flag)来完成
 */
class StopThread implements Runnable {
    private boolean flag = true;

    public void run() {
        while (flag) {
            try {
                //线程的任务是当前线程沉睡
                System.out.println("执行线程任务...");
                wait();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + "线程wait()出现异常,异常信息为:" + e);
                flag = false;
            }
            System.out.println(Thread.currentThread().getName() + "线程wait()");
        }
    }

    //修改标记
    public void setFlag() {
        flag = false;
    }
}