package currentLimiting.baseCode.jdk;


import currentLimiting.baseCode.Monitor;
import currentLimiting.baseCode.MonitorManage;
import currentLimiting.baseCode.ProductService;
import currentLimiting.baseCode.ProductServiceImpl;

/**
 * 静态代理
 */
public class ProductServiceProxy implements ProductService {

    private ProductService target;

    public ProductServiceProxy(ProductService target) {
        this.target = target;
    }

    @Override
    public void list() throws Exception {
        Monitor monitor = MonitorManage.getMonitor("list");
        if (monitor != null)
            monitor.checkRequestCount();
        this.target.list();
    }

    @Override
    public void info(String name) throws Exception {
        Monitor monitor = MonitorManage.getMonitor("info");
        if (monitor != null)
            monitor.checkRequestCount();
        this.target.info(name);
    }

    public static void main(String[] args) {
        ProductServiceProxy proxy = new ProductServiceProxy(new ProductServiceImpl());
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int count = 0;
                try {
                    while (true) {
                        proxy.info("iPhoneX");
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
