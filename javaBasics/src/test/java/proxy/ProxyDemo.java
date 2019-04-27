package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo implements InvocationHandler {

    private Object teacher;

    public ProxyDemo(Object teacher) {
        this.teacher = teacher;
    }

    public void before() {
        System.out.println("某某助理：上课前通知学生来上课");
    }

    public void after() {
        System.out.println("某某助理：上课后通知学生，需要做作业");
    }

    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //具体的业务调用 执行
        this.before(); //   课前通知
        Object result = method.invoke(teacher, args); //具体业务
        this.after();  //  课后通知
        return result;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(teacher.getClass().getClassLoader(), teacher.getClass().getInterfaces(), this);
    }

}