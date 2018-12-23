package designPatterns.proxy.javaProxy.base;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/25]
 */
public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    MyInvocationHandler() {
        super();
    }
    MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
            System.out.println("方法 " + method.getName() + "()的前置增强......");
            Object result = method.invoke(target, args);
            System.out.println("方法 " + method.getName() + "()的后置增强......");
            return result;
    }
}

