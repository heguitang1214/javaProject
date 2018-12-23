package designPatterns.proxy.cglib.factory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/30]
 */
public class Proxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("拦截前...");

        // 输出参数类型
        for (Object arg : objects) {
            System.out.print(arg.getClass() + ";");
        }
        Object result = methodProxy.invokeSuper(o, objects);

        System.out.println("拦截后...");

        return result;
    }
}
