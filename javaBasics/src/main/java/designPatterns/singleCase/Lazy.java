package designPatterns.singleCase;


/**
 * @author he_guitang
 * @version [1.0 , 2018/8/14]
 *  懒汉式(双重校验锁模式)
 *      延迟加载
 */
public class Lazy {

    //1.构造器私有化
    private Lazy(){}

    //2.内部创建对象
    private static Lazy single = null;


    /**
     * 3.获取返回的单例对象
     *      -双重校验锁模式
     */
    public static Lazy getInstance(){
        /*
          以后创建对象,不需要再进行锁的判断,提升效率
         */
        if (single == null){//解决效率问题
            synchronized (Lazy.class){
                //防止多个线程同时第一判空通过
                if (single == null){//解决安全问题
                    single = new Lazy();
                }
            }
        }
        return single;
    }

}
