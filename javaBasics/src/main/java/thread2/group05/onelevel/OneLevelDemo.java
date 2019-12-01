package thread2.group05.onelevel;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-16 20:32
 * @Description: 一级关联
 */
public class OneLevelDemo {
    public static void main(String[] args) {
        TestThread testThread1 = new TestThread();
        TestThread testThread2 = new TestThread();
        ThreadGroup threadGroup = new ThreadGroup("新建线程组1");
        Thread t0 = new Thread(threadGroup, testThread1);
        Thread t1 = new Thread(threadGroup, testThread2);
        t0.start();
        t1.start();
        System.out.println("活动的线程数为：" + threadGroup.activeCount());
        System.out.println("线程组的名称为：" + threadGroup.getName());
    }
}
