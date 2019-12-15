package thread2.t10_threadpool.folkjoin.recursiveaction;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-21 21:08
 * @Description: ForkJoin线程池使用 无返回值
 * <p>
 * ForkJoinPool的优势在于，可以充分利用多cpu，多核cpu的优势，
 * 把一个大任务拆分成多个“小任务”，把多个“小任务”放到多个处理器核心上并行执行；
 * 当多个“小任务”执行完成之后，再将这些执行结果合并起来即可.
 * <p>
 * 创建了ForkJoinPool实例之后，就可以调用ForkJoinPool的submit(ForkJoinTask<T> task)
 * 或invoke(ForkJoinTask<T> task)方法来执行指定任务了.
 * <p>
 * 其中ForkJoinTask代表一个可以并行、合并的任务。ForkJoinTask是一个抽象类，
 * 它还有两个抽象子类：RecursiveAction和RecursiveTask。
 * 其中RecursiveTask代表有返回值的任务，而RecursiveAction代表没有返回值的任务。
 */
public class ForkJoinPoolAction {

    public static final Set<Integer> RESULT_SET = new CopyOnWriteArraySet<>();

    public static void main(String[] args) throws Exception {
        // 验证CopyOnWriteArraySet功能 —— 去重
//        validateSet(RESULT_SET);
        // 验证FolkJoinPool功能
        validateFolkJoin();
    }

    /**
     * 验证FolkJoinPool功能
     *
     * @throws InterruptedException 中断异常
     */
    private static void validateFolkJoin() throws InterruptedException {
        // 需求：简单打印1-3000的数字
        // 程序将一个大任务拆分成多个小任务
        // 并将任务交给ForkJoinPool来执行
        PrintTask task = new PrintTask(0, 3000);
        //创建线程池
        ForkJoinPool pool = new ForkJoinPool();
        //将task提交至线程池
        pool.submit(task);
        //线程阻塞，等待所有任务完成
        pool.awaitTermination(2, TimeUnit.SECONDS);

        System.out.printf("RESULT_SET的大小=%s", RESULT_SET.size());
        pool.shutdown();
    }

    /**
     * 验证CopyOnWriteArraySet功能 —— 去重
     *
     * @param resultSet 结果集
     */
    private static void validateSet(Set<Integer> resultSet) {
        resultSet.add(1);
        resultSet.add(1);
        System.out.printf("resultSet大小=%s", resultSet.size());
    }
}


