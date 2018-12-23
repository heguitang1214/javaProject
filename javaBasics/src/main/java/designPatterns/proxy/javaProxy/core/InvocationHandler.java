package designPatterns.proxy.javaProxy.core;

import java.lang.reflect.Method;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/22]
 */

public interface InvocationHandler {
    void invokeMethod(Object o, Method method);
}


