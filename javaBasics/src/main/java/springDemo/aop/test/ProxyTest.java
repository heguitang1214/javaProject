package springDemo.aop.test;

import springDemo.aop.interceptor.LogInterceptor;
import springDemo.ioc.dao.UserDAO;
import springDemo.ioc.dao.impl.UserDAOImpl;
import springDemo.ioc.model.User;

import java.lang.reflect.Proxy;

/**
 * Created by 11256 on 2018/8/26.
 * AOP动态代理测试
 */
public class ProxyTest {


    public static void main(String[] args) {
        testProxy();
    }


    private static void testProxy() {
        UserDAO userDAO = new UserDAOImpl();
        LogInterceptor li = new LogInterceptor();
        li.setTarget(userDAO);
        /*
            产生的代理对象根据参数中的接口产生,生成的代理对象实现所需要的接口
            目标的实现交给了代理对象来实现(添加增强的逻辑)
         */
        UserDAO userDAOProxy = (UserDAO) Proxy.newProxyInstance(
                userDAO.getClass().getClassLoader(),
//                userDAO.getClass().getInterfaces(),
                new Class[]{UserDAO.class},
                li);
        System.out.println(userDAOProxy.getClass());
        userDAOProxy.save(new User());

    }


}
