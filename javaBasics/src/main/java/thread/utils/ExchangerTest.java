package thread.utils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 交换线程的数据：实现线程数据的交换
 *  只能两个线程进行交换，如果只有一个线程，数据交换就会失败。
 *  如果线程数大于2个线程，并且为奇数，就会有一个线程的数据交换失败，为偶数，能够交换成功。
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<>();
        service.execute(new Runnable() {
            public void run() {
                dataExchange(exchanger, "[语文]");
//                try {
//                    String data = "[语文]";
//                    System.out.println("线程" + Thread.currentThread().getName() +
//                            "正在把数据" + data + "换出去");
//                    Thread.sleep((long) (Math.random() * 10000));
//                    String data2 = exchanger.exchange(data);
//                    System.out.println("线程" + Thread.currentThread().getName() +
//                            "换回的数据为" + data2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });

        service.execute(new Runnable() {
            public void run() {
                dataExchange(exchanger, "[数学]");
//                try {
//                    String data = "[数学]";
//                    System.out.println("线程" + Thread.currentThread().getName() +
//                            "正在把数据" + data + "换出去");
//                    Thread.sleep((long) (Math.random() * 10000));
//                    String data2 = exchanger.exchange(data);
//                    System.out.println("线程" + Thread.currentThread().getName() +
//                            "换回的数据为" + data2);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });


//        service.execute(new Runnable() {
//            public void run() {
//                dataExchange(exchanger, "[英语]");
//            }
//        });
//
//        service.execute(new Runnable() {
//            public void run() {
//                dataExchange(exchanger, "[美术]");
//            }
//        });


    }

    private static void dataExchange(Exchanger<String> exchanger, String data){
        try {
            System.out.println("线程" + Thread.currentThread().getName() +
                    "正在把数据" + data + "换出去");
            Thread.sleep((long) (Math.random() * 10000));
            String data2 = exchanger.exchange(data);
            System.out.println("线程" + Thread.currentThread().getName() +
                    "换回的数据为" + data2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

