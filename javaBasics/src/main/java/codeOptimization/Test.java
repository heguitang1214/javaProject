package codeOptimization;

import java.math.BigDecimal;

/**
 * @Author heguitang
 * @Date 2019/2/18 16:19
 * @Version 1.0
 * @Desc 测试
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Context context = new Context();
        BigDecimal bigDecimal = context.calRecharge(1, 20);
        System.out.println(bigDecimal);

        BigDecimal bigDecimal1 = context.calRecharge(2, 50);
        System.out.println(bigDecimal1);
    }

}
