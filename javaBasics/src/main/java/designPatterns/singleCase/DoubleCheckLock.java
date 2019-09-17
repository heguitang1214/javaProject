package designPatterns.singleCase;


/**
 * @author he_guitang
 * @version [1.0 , 2018/8/14]
 * 懒汉式(双重校验锁模式)
 * 延迟加载
 */
public class DoubleCheckLock {

    /**
     * 1.构造器私有化
     */
    private DoubleCheckLock() {
    }

    /**
     * 2.内部创建对象，volatile是为了双重校验的时候，发生指令重排
     */
    private static volatile DoubleCheckLock single = null;

    /**
     * 3.获取返回的单例对象
     * -双重校验锁模式
     * A线程进来，为空，进入锁，然后挂起，B线程判空，不能进入锁中；
     * 当A线程创建对象后，B线程再进入，已经有了对象，不再创建对象(这种情况下第一次判空没有效率提升)。
     * 但是当再有一个线程C进入的时候，就直接进行第一个判断，不会再进入锁里面。
     * 即：第一次判断会消耗资源，以后就提高了速度。
     * 懒汉式是实例的延迟加载。
     */
    public static DoubleCheckLock getInstance() {

        // 以后创建对象,不需要再进行锁的判断,提升效率
        // 解决效率问题
        if (single == null) {
            synchronized (DoubleCheckLock.class) {
                //防止多个线程同时进行锁判断，第一个线程通过后，后续线程继续创建新的对象
                //解决安全问题
                if (single == null) {
                    single = new DoubleCheckLock();
                }
            }
        }
        return single;
    }

}
