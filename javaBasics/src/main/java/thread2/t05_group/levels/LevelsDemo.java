package thread2.t05_group.levels;


import thread2.t05_group.onelevel.TestThread;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-06-16 20:40
 * @Description: 多级关联
 */
public class LevelsDemo {
    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("线程组1");
        ThreadGroup threadGroup2
                = new ThreadGroup(threadGroup1, "线程组2");
        ThreadGroup threadGroup3
                = new ThreadGroup(threadGroup1, "线程组3");
        TestThread testThread1 = new TestThread();
        TestThread testThread2 = new TestThread();
        TestThread testThread3 = new TestThread();
        Thread t0 = new Thread(threadGroup1, testThread1);
        Thread t1 = new Thread(threadGroup2, testThread2);
        Thread t2 = new Thread(threadGroup3, testThread3);
        t0.start();
        t1.start();
        t2.start();
        System.out.println("threadGroup1线程组的名称为：" + threadGroup1.getName());
        System.out.println("threadGroup1活动的线程数为：" + threadGroup1.activeCount());
        System.out.println("threadGroup1活动的线程组数为：" + threadGroup1.activeGroupCount());
        System.out.println("threadGroup2线程组的名称为：" + threadGroup2.getName());
        System.out.println("threadGroup2活动的线程数为：" + threadGroup2.activeCount());
        System.out.println("threadGroup3线程组的名称为：" + threadGroup3.getName());
        System.out.println("threadGroup3活动的线程数为：" + threadGroup3.activeCount());
    }
}
