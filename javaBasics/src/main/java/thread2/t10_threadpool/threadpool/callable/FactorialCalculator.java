package thread2.t10_threadpool.threadpool.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-22 21:26
 * @Description: ForkJoin线程池使用  有返回值
 */
public class FactorialCalculator implements Callable<String> {

    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public String call() throws Exception {
        int result = 1;

        if (number == 0 || number == 1) {
            result = 1;
        } else {
            for (int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MICROSECONDS.sleep(200);
            }
        }
        return String.format("%s输出%d的阶乘为：%d\n",
                Thread.currentThread().getName(), number, result);
    }
}