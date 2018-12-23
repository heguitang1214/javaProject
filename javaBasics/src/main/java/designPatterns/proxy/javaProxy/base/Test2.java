package designPatterns.proxy.javaProxy.base;

import java.lang.reflect.Proxy;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/25]
 *      java的proxy
 *      多个接口的增强器
 */
public class Test2 {
    public static void main(String[] args) {
        IUserDao userService = new UserDao();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(userService);
        IUserDao proxy = (IUserDao) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                new Class<?>[] {IUserDao.class, IUserInfoDao.class},
                invocationHandler);
        proxy.save();
//    方法 save()的前置增强......
//    保存数据操作----
//    方法 save()的后置增强......
        System.out.println("==================================================================");
        IUserInfoDao proxy1 = (IUserInfoDao) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                new Class<?>[] {IUserDao.class, IUserInfoDao.class},
                invocationHandler);
        proxy1.update();
//        方法 update()的前置增强......
//        用户补充:修改数据操作----
//        方法 update()的后置增强......
    }
}





