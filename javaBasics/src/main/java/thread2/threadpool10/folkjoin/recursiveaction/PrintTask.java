package thread2.threadpool10.folkjoin.recursiveaction;

import java.util.concurrent.RecursiveAction;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-21 21:21
 * @Description: ForkJoin线程池使用
 */
public class PrintTask extends RecursiveAction {
    /**
     * 最多只能打印50个数
     */
    private static final int THRESHOLD = 50;
    private int start;
    private int end;

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                ForkJoinPoolAction.RESULT_SET.add(i);
                System.out.println(Thread.currentThread().getName() + "的i值\t" + i);
            }
        } else {
            //递归切分
            int mid = (start + end) / 2;
            PrintTask leftTask = new PrintTask(start, mid);
            PrintTask rightTask = new PrintTask(mid, end);
            //并行执行两个任务
            leftTask.fork();
            rightTask.fork();
        }
    }

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

}
