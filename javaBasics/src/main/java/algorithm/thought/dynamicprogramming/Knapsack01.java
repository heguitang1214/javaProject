package algorithm.thought.dynamicprogramming;

import java.util.Arrays;

/**
 * 背包问题
 */
public class Knapsack01 {

    /**
     * 结果放到 maxW 中
     */
    private static int maxW = Integer.MIN_VALUE;

    /**
     * 物品个数
     */
    private static int n = 5;

    /**
     * 背包承受的最大重量
     */
    private static int w = 9;

    /**
     * 物品重量
     */
    private static int[] weight = {2, 2, 4, 6, 3};

    /**
     * 备忘录，默认值 false
     * 递归树中的每个节点表示一种状态，我们用（i, cw）来表示。
     * 其中，i 表示将要决策第几个物品是否装入背包，cw 表示当前背包中物品的总重量。
     * 这里表示men也是：mem[i][cw]，只不过是标志位
     */
    private static boolean[][] mem = new boolean[5][10];

    /**
     * 背包问题:递归备忘录方式解决
     * 调用 f(0, 0)
     *
     * @param i  将要决策第几个物品是否装入背包
     * @param cw 当前背包中物品的总重量
     */
    public static void f(int i, int cw) {
        // cw==w 表示装满了，i==n 表示物品都考察完了
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 重复状态
        if (mem[i][cw]) {
            return;
        }
        // 记录 (i, cw) 这个状态
        mem[i][cw] = true;
        // 选择不装第 i 个物品
        f(i + 1, cw);
        if (cw + weight[i] <= w) {
            // 选择装第 i 个物品
            f(i + 1, cw + weight[i]);
        }
    }


    /**
     * 动态规划：0-1背包问题
     * weight: 物品重量，n: 物品个数，w: 背包可承载重量
     */
    private static int knapsack(int[] weight, int n, int w) {
        // 默认值 false
        boolean[][] states = new boolean[n][w + 1];
        // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][0] = true;
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }

        // 动态规划状态转移
        for (int i = 1; i < n; ++i) {
            // 不把第 i 个物品放入背包
            for (int j = 0; j <= w; ++j) {
                // states[i - 1][j] == true
                if (states[i - 1][j]) {
                    states[i][j] = states[i - 1][j];
                }
            }
            // 把第 i 个物品放入背包
            for (int j = 0; j <= w - weight[i]; ++j) {
                if (states[i - 1][j]) {
                    states[i][j + weight[i]] = true;
                }
            }
        }
        // 输出结果
        for (int i = w; i >= 0; --i) {
            if (states[n - 1][i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 动态规划：0-1背包问题，空间优化
     *
     * @param weight 物品重量数组
     * @param n      物品个数
     * @param w      背包可承载重量
     * @return 最优值
     */
    private static int knapsack2(int[] weight, int n, int w) {
        // 默认值 false
        boolean[] states = new boolean[w + 1];
        // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0] = true;
        if (weight[0] <= w) {
            states[weight[0]] = true;
        }
        System.out.println(Arrays.toString(states));
        // 动态规划，i代表物品的个数，j代表的是【剩余背包可承载重量】
        for (int i = 1; i < n; ++i) {
            // 把第 i 个物品放入背包，当当前物品可以放入背包的时候，依次向前考察数据，
            // 得到 states[j + items[i]] 的值，即：根据前一个值获取后一个值
            // 减少了计算量，从前往后循环，也是可以的，但是存在重复计算的问题
            // 默认的是不放，只要计算放的就可以
            for (int j = w - weight[i]; j >= 0; --j) {
                if (states[j]) {
                    states[j + weight[i]] = true;
                }
            }

            // 从前往后循环：存在重复计算的问题
//            for (int j = 0; j < w; j++) {
//                if (j + items[i] <= w) {
//                    if (states[j]) {
//                        states[j + items[i]] = true;
//                    }
//                }
//            }
            System.out.println(Arrays.toString(states));
        }

        // 输出结果
        for (int i = w; i >= 0; --i) {
            if (states[i]) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 动态规划-背包问题升级：对于一组不同重量、不同价值、不可分割的物品，我们选择将某些物品装入背包，在满足背包最大重量限制的前提下，
     * 背包中可装入物品的总价值最大是多少呢？
     *
     * @param weight 物品重量数组
     * @param value  物品价值数组
     * @param n      物品个数
     * @param w      背包可承载重量
     * @return 最优值
     */
    private static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        // 初始化 states
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }

        // 动态规划，状态转移
        for (int i = 1; i < n; ++i) {
            // 不选择第 i 个物品，其值就是前面使用的值
            for (int j = 0; j <= w; ++j) {
                if (states[i - 1][j] >= 0) {
                    states[i][j] = states[i - 1][j];
                }
            }
            // 选择第 i 个物品
            for (int j = 0; j <= w - weight[i]; ++j) {
                if (states[i - 1][j] >= 0) {
                    // 选择当前物品后的价值
                    int v = states[i - 1][j] + value[i];
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }

        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) {
                maxvalue = states[n - 1][j];
            }
        }
        return maxvalue;
    }

    /**
     * 动态规划-背包问题升级，空间优化
     *
     * @param weight 物品重量数组
     * @param value  物品价值数组
     * @param w      背包可承载重量
     * @return 最优值
     */
    private static int knapsack4(int[] weight, int[] value, int w) {
        int[] results = new int[w + 1];

        for (int i = 1; i <= weight.length; i++) {
            for (int j = w; j >= 1; j--) {
                if (j >= weight[i - 1]) {
                    results[j] = Math.max(results[j], results[j - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        // 输出结果
        return results[w];
    }


    public static void main(String[] args) {
        f(0, 0);
        System.out.println("最大容量为：" + maxW);

        int result = knapsack(weight, n, w);
        System.out.println("最大容量为：" + result);

        int knapsack2 = knapsack2(weight, n, w);
        System.out.println("最大容量为：" + knapsack2);

        int[] weight = {2, 2, 4, 6, 3};
        int[] value = {3, 4, 8, 9, 6};
        int knapsack3 = knapsack3(weight, value, 5, 9);
        System.out.println("背包能装的最大价值为：knapsack3=" + knapsack3);

//        int knapsack4 = knapsack4(weight, value, 5, 9);
        int knapsack4 = knapsack4(weight, value, 9);
        System.out.println("背包能装的最大价值为：knapsack4=" + knapsack4);
    }

}
