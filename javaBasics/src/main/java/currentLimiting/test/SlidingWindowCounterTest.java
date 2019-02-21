package currentLimiting.test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlidingWindowCounterTest {
  
    private static ExecutorService es = Executors.newCachedThreadPool();
    private static ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args) throws IOException {
        testNWindow();
    }

//    @Test
    public static void testNWindow() throws IOException {
        SlidingWindowCounter swc = new SlidingWindowCounter(3);  
        ses.scheduleAtFixedRate(() -> {  
            Loops.fixLoop(swc::increase, new Random().nextInt(10));
        }, 10, 2, TimeUnit.MILLISECONDS);
        ses.scheduleAtFixedRate(() -> {  
            System.out.println(swc);  
            swc.advance();  
        }, 1, 1, TimeUnit.SECONDS);  
        ses.scheduleAtFixedRate(() -> {  
            swc.resizeWindow(new Random().nextInt(10));  
        }, 1, 10, TimeUnit.SECONDS);  
        System.in.read();  
    }  
  
  
//    @Test
    public static void test1Window() {
        SlidingWindowCounter swc = new SlidingWindowCounter(1);  
        System.out.println(swc);  
        swc.increase();  
        swc.increase();  
        System.out.println(swc);  
        swc.advance();  
        System.out.println(swc);  
        swc.increase();  
        swc.increase();  
        System.out.println(swc);  
    }  
  
//    @Test
    public static void test3Window() {
        SlidingWindowCounter swc = new SlidingWindowCounter(3);  
        System.out.println(swc);  
        swc.increase();  
        System.out.println(swc);  
        swc.advance();  
        System.out.println(swc);  
        swc.increase();  
        swc.increase();  
        System.out.println(swc);  
        swc.advance();  
        System.out.println(swc);  
        swc.increase();  
        swc.increase();  
        swc.increase();  
        System.out.println(swc);  
        swc.advance();  
        System.out.println(swc);  
        swc.increase();  
        swc.increase();  
        swc.increase();  
        swc.increase();  
        System.out.println(swc);  
        swc.advance();  
        System.out.println(swc);  
    }  
  
}  