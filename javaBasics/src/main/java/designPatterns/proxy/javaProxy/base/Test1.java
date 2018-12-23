package designPatterns.proxy.javaProxy.base;


/**
 * @author he_guitang
 * @version [1.0 , 2018/5/24]
 *  单个对象的事物增强操作
 */
public class Test1 {
    public static void main(String[] args) {
        // 目标对象
        IUserDao target = new UserDao();
        //原始的类型
        System.out.println(target.getClass());
        /*给目标对象，创建代理对象
           -传递给具体的增强代理器操作(将原始对象进行增强)
          */
        IUserDao proxy = (IUserDao) new TransactionProxy(target).getProxyInstance();
        // proxy为内存中动态生成的代理对象
        System.out.println(proxy.getClass());
        // 代理对象执行对应的方法,完成增强
        proxy.save();
//        proxy.update();

    }
//打印结果
//     class designPatterns.proxy.javaProxy.UserDao
//    class com.sun.proxy.$Proxy0
//    开始事务......
//    保存数据操作----
//    提交事务......
}


