package datastructure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author heguitang
 * @Date 2019/3/8 17:29
 * @Version 1.0
 * @Desc 抢红包算法
 */
public class RedEnvelopes {

    /**
     * 发红包算法，金额参数以分为单位
     * @param totalAmount 总金额
     * @param totalPeopleNum 总个数
     * @return 每个金额
     */
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {
        List<Integer> amountList = new ArrayList<>();
        Integer restAmount = totalAmount;
        Integer restPeopleNum = totalPeopleNum;
        Random random = new Random();
        for (int i = 0; i < totalPeopleNum - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            amountList.add(amount);
        }
        amountList.add(restAmount);

        return amountList;
    }

    public static void main(String[] args) {
        List<Integer> amountList = divideRedPackage(5000, 30);
        for (Integer amount : amountList) {
            System.out.println("抢到金额：" + new BigDecimal(amount).divide(new BigDecimal(100)));
        }
    }

}
