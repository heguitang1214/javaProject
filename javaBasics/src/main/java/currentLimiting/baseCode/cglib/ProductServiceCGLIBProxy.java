package currentLimiting.baseCode.cglib;

import currentLimiting.baseCode.Monitor;
import currentLimiting.baseCode.MonitorManage;
import currentLimiting.baseCode.ProductServiceImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB实现接口的代理
 */
public class ProductServiceCGLIBProxy<T> implements MethodInterceptor {

    private T target;

    public ProductServiceCGLIBProxy(T target) {
        this.target = target;
    }

    public T create() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    /**
     * @param o           代理对象
     * @param method      目标对象方法
     * @param objects     目标对象入参
     * @param methodProxy 方法代理对象
     * @return Object
     * @throws Throwable 异常
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        Monitor monitor = MonitorManage.getMonitor(method.getName());
        if (monitor != null)
            monitor.checkRequestCount();
        result = methodProxy.invokeSuper(o, objects);
        return result;
    }

    public static void main(String[] args) throws Exception {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\\\hhb\\云犀\\course\\public\\proxy");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\Resources\\javaProject\\javaBasics\\src\\main\\java\\currentLimiting\\cglib");

        ProductServiceCGLIBProxy<ProductServiceImpl> proxy = new ProductServiceCGLIBProxy(new ProductServiceImpl());
        ProductServiceImpl productServiceImpl = proxy.create();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int count = 0;
                try {
                    while (true) {
                        productServiceImpl.info("iPhoneX");
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
