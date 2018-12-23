package designPatterns.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib子类代理工厂:
 *  一个基于ASM的字节码生成库，它允许我们在运行时对字节码进行修改和动态生成(对UserDao在内存中动态构建一个子类对象)
 *  采用的是继承，所以不能对final修饰的类进行代理。
 *
 * 1.首先实现一个MethodInterceptor，方法调用会被转发到该类的intercept()方法。
 */
public class ProxyFactory implements MethodInterceptor {
    //维护目标对象
    private Object target;

    ProxyFactory(Object target) {
        this.target = target;
    }

    /*
        给目标对象创建一个代理对象
        然后在需要使用target的时候，通过CGLIB动态代理获取代理对象。
        -我们通过CGLIB的Enhancer来指定要代理的目标对象、实际处理代理逻辑的对象，
        最终通过调用create()方法得到代理对象，对这个对象所有非final方法的调用都会转发给MethodInterceptor.intercept()方法，
        在intercept()方法里我们可以加入任何逻辑，比如修改方法参数，加入日志功能、安全检查功能等；
        通过调用MethodProxy.invokeSuper()方法，我们将调用转发给原始对象，具体到本例，就是UserDao的具体方法。
        CGLIG中MethodInterceptor的作用跟JDK代理中的InvocationHandler很类似，都是方法调用的中转站。
     */
    Object getProxyInstance() {
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类(目标类)
        en.setSuperclass(target.getClass());
        //3.设置回调函数,使用这个类的方法的时候,会被拦截(intercept)进行增强,
        // 该类需要实现MethodInterceptor接口
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
        System.out.println("开始事务...");
        //执行目标对象的方法
//        Object returnValue = method.invoke(target, args);
        /*
            表示调用原始类的被拦截到的方法。这个方法的前后添加需要的过程。在这个方法中，我们可以在调用原方法之前或之后注入自己的代码。
            由于性能的原因，对原始方法的调用使用CGLIB的net.sf.cglib.proxy.MethodProxy对象，
            而不是反射中一般使用java.lang.reflect.Method对象。
         */
        Object returnValue = proxy.invokeSuper(obj, args);
        System.out.println("提交事务...");
//        return returnValue;
        return returnValue;
    }
}



