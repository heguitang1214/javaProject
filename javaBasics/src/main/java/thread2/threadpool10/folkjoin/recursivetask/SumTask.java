package thread2.threadpool10.folkjoin.recursivetask;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-22 20:32
 * @Description: ForkJoin线程池使用
 */
public class SumTask extends RecursiveTask<Integer> {
    /**
     * 每个小任务，最多只累加20个数
     */
    private static final int THRESHOLD = 20;
    private int nums[];
    private int start;
    private int end;

    public SumTask(int[] nums, int start, int end) {
        super();
        this.nums = nums;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += nums[i];
            }
            return sum;
        } else {
            //当分块超过阈值时，则需要对数据进行拆分
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(nums, start, mid);
            SumTask rightTask = new SumTask(nums, mid, end);
            //并行执行两个小任务
            leftTask.fork();
            rightTask.fork();
            //把两个小任务累加合并
            return leftTask.join() + rightTask.join();
        }
    }
}
