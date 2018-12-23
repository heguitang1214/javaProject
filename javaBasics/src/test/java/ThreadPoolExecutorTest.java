import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 11256 on 2018/9/8.
 * 线程池参数的测试
 */
public class ThreadPoolExecutorTest {


    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("1212");
            }
        };

        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);
        blockingQueue.add(runnable);

//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, MILLISECONDS, blockingQueue);


    }


}
