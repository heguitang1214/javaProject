package thread.multiBase;

/**
 * Created by heguitang on 2019/2/1.
 * 测试虚拟机关闭的时候，发送信息
 */
public class RuntimeDemo {


    public static void main(String[] args) {


        System.out.println(1111111);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaaaa");
            }
        });
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(thread);

        System.out.println(222);
    }




}
