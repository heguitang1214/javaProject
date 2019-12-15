package thread2.t04_priority.systematicness;


import java.util.Random;

/**
 */
public class MyThread1 extends Thread {
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("------1------ thread 1 start running");
        long count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 50000; j++) {
                Random random = new Random();
                random.nextInt();
                count = count + i;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("------1------ thread 1 use time = " + (end - start));
    }
}

