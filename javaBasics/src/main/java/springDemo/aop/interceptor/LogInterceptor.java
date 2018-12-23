package springDemo.aop.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 11256 on 2018/8/26.
 * 日志拦截器
 * AOP实现的原理
 */
public class LogInterceptor implements InvocationHandler {

    //目标对象(源)
    private Object target;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    private void beforeMethod(Method method){
        System.out.println(method.getName() + " start.....");
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        beforeMethod(method);
        //目标对象的方法,也就是源逻辑,需要增强的目标方法
        method.invoke(target, args);
        return null;
    }




}
