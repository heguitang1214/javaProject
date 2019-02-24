package currentLimiting.baseCode.jdk;


import currentLimiting.baseCode.Monitor;
import currentLimiting.baseCode.MonitorManage;
import currentLimiting.baseCode.ProductService;
import currentLimiting.baseCode.ProductServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 动态代理
 */
public class ProductServiceDynamicProxy<T> implements InvocationHandler {

    private T target;

    public ProductServiceDynamicProxy(T target) {
        this.target = target;
    }

    public T create() {
        //绑定该类实现的所有接口，取得代理类
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);

        return (T) proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Monitor monitor = MonitorManage.getMonitor(method.getName());
        if (monitor != null)
            monitor.checkRequestCount();
        return method.invoke(target, args);
    }

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        ProductServiceDynamicProxy<ProductService> proxy = new ProductServiceDynamicProxy<>(new ProductServiceImpl());
        ProductService productService = proxy.create();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int count = 0;
                try {
                    while (true) {
                        productService.info("iPhoneX");
                        count++;
                    }
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "调用次数:" + count);
                    System.err.println(e.getMessage());
                }
            }, "Thread-" + i + "-info").start();
        }
    }
}
