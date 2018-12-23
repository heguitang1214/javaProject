package designPatterns.proxy.javaProxy.promotion;

import designPatterns.proxy.javaProxy.core.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/22]
 */

public class TimeHandler implements InvocationHandler {
    private Object object;

    public TimeHandler(Object object){
//        super();
        this.object = object;
    }
    @Override
    public void invokeMethod(Object o, Method method) {
        System.out.println("时间管理器开始......");
        try {
            method.invoke(object);//反射的实现
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("时间管理器结束......");
    }
}


