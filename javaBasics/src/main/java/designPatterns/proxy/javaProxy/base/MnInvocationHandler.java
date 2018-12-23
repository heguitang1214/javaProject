package designPatterns.proxy.javaProxy.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/25]
 */
public class MnInvocationHandler implements InvocationHandler {

    private Object target;

    MnInvocationHandler() {
        super();
    }

    MnInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法 " + method.getName() + "()的前置增强1......");
        Object result = method.invoke(target, args);
        System.out.println("方法 " + method.getName() + "()的后置增强1......");
        return result;
    }
}
