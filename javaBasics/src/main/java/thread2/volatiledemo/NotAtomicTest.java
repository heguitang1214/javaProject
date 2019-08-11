package thread2.volatiledemo;

/**
 * volatile非原子性测试：volatile不能保证原子性
 */
public class NotAtomicTest {
    public volatile int inc = 0;

    public void increase() {
        // inc++是非原子性操作
        // inc = inc + 1;
        inc++;
    }

    public static void main(String[] args) throws InterruptedException {
        final NotAtomicTest test = new NotAtomicTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    test.increase();
                }
            }).start();
        }

        Thread.sleep(3000);
        System.out.println(test.inc);
    }
}
