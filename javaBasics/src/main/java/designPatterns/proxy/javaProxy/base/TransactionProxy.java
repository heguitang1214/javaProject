package designPatterns.proxy.javaProxy.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/24]
 */

class TransactionProxy {
    //维护一个目标对象(传递进来的对象)
    private Object target;
    TransactionProxy(Object target){
        this.target = target;
    }

    //给目标对象生成代理对象
    Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),//获取对象,指定加载器
                target.getClass().getInterfaces(),//绑定接口(目标接口,也就是需要增强的的接口)
                new InvocationHandler() {//增强逻辑(具体的增强逻辑,相当于TimeHandler...)
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始事务......");
                        //执行目标对象方法(反射进行实现)，方法参数target是实际的被代理对象，
                        // args为执行被代理对象相应操作所需的参数。
                        Object returnValue = method.invoke(target, args);
                        System.out.println("提交事务......");
                        //将传递的对象返回回去,即可使用对应的接口接收数据(需要一个实现了InvocationHandler接口的实现类)
                        return returnValue;
                    }
                }
        );
    }
}


