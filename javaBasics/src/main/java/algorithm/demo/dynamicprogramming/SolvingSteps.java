package algorithm.demo.dynamicprogramming;


import java.util.HashMap;
import java.util.Map;

/**
 * 求解台阶问题
 * 有一座高度是10级台阶的楼梯，从下往上走，每跨一步只能向上1级或者2级台阶。要求用程序来求出一共有多少种走法。\
 * 状态转移公式：F(N) = F(N-1) + F(N-2)
 *
 * @author Tang
 */
public class SolvingSteps {

    /**
     * 递归求解：n阶台阶的走法
     *
     * @param n 台阶数量
     */
    private static int getClimbingWays(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        return getClimbingWays(n - 1) + getClimbingWays(n - 2);
    }


    /**
     * 备忘录算法：n阶台阶的走法
     *
     * @param n   台阶数量
     * @param map map缓存
     * @return 台阶走法
     */
    private static int getClimbingWaysV2(int n, HashMap<Integer, Integer> map) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        printMap(map);

        if (map.containsKey(n)) {
            return map.get(n);
        } else {
            int value = getClimbingWaysV2(n - 1, map) + getClimbingWaysV2(n - 2, map);
            map.put(n, value);
            return value;
        }
    }


    /**
     * 动态规划求解：n阶台阶的走法
     *
     * @param n 台阶数量
     */
    private static int getClimbingWaysV3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int a = 1;
        int b = 2;
        int temp = 0;

        for (int i = 3; i <= n; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
        return temp;
    }


    /**
     * 打印map
     *
     * @param map 散列表
     */
    private static void printMap(Map<Integer, Integer> map) {
        System.out.println("====================当前Map======================");
        for (Integer key : map.keySet()) {
            System.out.println("key=" + key + ",value=" + map.get(key));
        }
    }

    public static void main(String[] args) {
        System.out.println("===================递归求解==========================");
        System.out.println(getClimbingWays(10));

        System.out.println("===================备忘录算法==========================");
        System.out.println(getClimbingWaysV2(10, new HashMap<>()));

        System.out.println("===================动态规划求解==========================");
        System.out.println(getClimbingWaysV3(10));

    }


}
