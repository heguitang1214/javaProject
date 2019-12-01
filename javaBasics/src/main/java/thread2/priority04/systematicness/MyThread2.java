package thread2.priority04.systematicness;

import java.util.Random;

/**
 */
public class MyThread2 extends Thread {
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("------2------ thread 2 start running");
        long count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 50000; j++) {
                Random random = new Random();
                random.nextInt();
                count = count + i;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("------2------ thread 2 use time = " + (end - start));
    }
}
