package currentLimiting.baseCode;


/**
 *
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public void list() throws Exception {
        System.out.println("iPhoneX");
        System.out.println("华为P20");
        System.out.println("小米MIX2");
    }

    @Override
    public void info(String name) throws Exception {
        Monitor monitor = MonitorManage.getMonitor("info");
        if (monitor != null){
            monitor.checkRequestCount();
        }
        System.out.println(name);
    }

    /**
     * 简易测试接口限流
     */
    public static void main(String[] args) {
        ProductServiceImpl productService = new ProductServiceImpl();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int count = 0;
                try {
                    while (true) {
                        productService.info("iphoneX");
                        count++;
                    }
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "调用次数:" + count);
                    System.err.println(e.getMessage());
                }
            }, "Thread-" + i + "info").start();
        }

//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                int count = 0;
//                try {
//                    while (true) {
//                        productService.list();
//                        count++;
//                    }
//                } catch (Exception e) {
//                    System.out.println(Thread.currentThread().getName() + "调用次数:" + count);
//                    System.err.println(e.getMessage());
//                }
//            }, "Thread-" + i + "-list").start();
//        }
    }
}
