package designPatterns.proxy.cglib;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/25]
 */

public class Test {
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        //代理对象
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();
        //执行代理对象的方法
        proxy.save();
    }
}



