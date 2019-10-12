package algorithm.demo.dynamicprogramming;

import java.util.Arrays;

/**
 * 求解金矿问题
 * 有一个国家发现了5座金矿，每座金矿的黄金储量不同，需要参与挖掘的工人数也不同。
 * 参与挖矿工人的总数是10人。每座金矿要么全挖，要么不挖，不能派出一半人挖取一半金矿。
 * 要求用程序求解出，要想得到尽可能多的黄金，应该选择挖取哪几座金矿？
 *
 * @author heguitang
 */
public class GoldMining {

    /**
     * 获得金矿最优收益
     * 时间复杂度为：O(2^n)
     *
     * @param w 工人数量
     * @param n 可选金矿数量
     * @param p 金矿开采所需工人数量
     * @param g 金矿储量
     */
    private static int getBestGoldMining(int w, int n, int[] p, int[] g) {
        if (w == 0 || n == 0) {
            return 0;
        }

        // 所剩工人不够挖掘当前金矿，p[n - 1]代表的是：n - 1代表的是最后一座金矿是挖还是不挖
        System.out.println("工人数量w=" + w + ",金矿开采所需工人数量p=" + p[n - 1]);
        if (w < p[n - 1]) {
            // 当前任务不足于挖掘最后一座金矿，移除当前金矿
            // 看能不能满足其他金矿的要求
            return getBestGoldMining(w, n - 1, p, g);
        }

        return Math.max(
                // 最后一个金矿不挖：工人数不减少，金矿数减少1
                getBestGoldMining(w, n - 1, p, g),
                // 最后一个金矿挖：工人减少为 w - p[n - 1]，金矿减少为 n-1，g[n - 1]为获得的金矿储量
                getBestGoldMining(w - p[n - 1], n - 1, p, g) + g[n - 1]);
    }


    /**
     * 获得金矿最优收益
     * 时间复杂度为：O(nw)
     *
     * @param w 工人数量
     * @param p 金矿开采所需工人数量
     * @param g 金矿储量
     */
    private static int getBestGoldMiningV2(int w, int[] p, int[] g) {
        // 创建表格，横坐标表示金矿数量，纵坐标表示所需工人数量， + 1是因为数组下标从0开始
        int[][] resultTable = new int[g.length + 1][w + 1];
        // 填充表格
        // 外层循环：金矿数量
        for (int i = 1; i <= g.length; i++) {
            // 内部循环：每次循环都添加一个工人
            for (int j = 0; j <= w; j++) {
                // 工人数量 < 金矿开采所需工人数量
                if (j < p[i - 1]) {
                    resultTable[i][j] = resultTable[i - 1][j];
                } else {
                    resultTable[i][j] = Math.max(
                            resultTable[i - 1][j],
                            // j - p[i - 1]：减少工人数量
                            resultTable[i - 1][j - p[i - 1]] + g[i - 1]);
                }
            }
        }
        // 返回最后一个格子的值
        return resultTable[g.length][w];
    }


    /**
     * 获得金矿最优收益：保存前一行的金矿数据，当前最优收益只依赖于前一行的数据（第一行除外）
     * 3个金矿8个人的结果，就是来自于2个金矿8工人和2个金矿5工人，max(500,500 + 200) = 700
     * 200 = 第三个金矿挖获得的金矿储量
     * <p>
     * 最优子结构和最终问题的依赖关系：也就是4个金矿的最优选择和5个金矿的最优选择之间，有什么样的关系？
     * （1）5个金矿的最优选择有两个，挖/不挖
     * （2）4个金矿的最优选择有两个，挖/不挖
     * （3）第5个金矿不挖的最优选择是：f(n，w)=f(n-1，w)，也就是第4个金矿的最优选择
     * （4）第5个金矿不挖的最优选择是：F(n,w) = max(F(n-1,w),  F(n-1,w-p[n-1])+g[n-1]) (n>1, w>=p[n-1])，
     * 也就是【前4个金矿n个工人的数量】与【前4个金矿n-p[4]工人的数量 + 第5个金矿的挖金数量】的最大值
     * <p>
     * 时间复杂度为：O(nw)
     *
     * @param w 工人数量
     * @param p 金矿开采所需工人数量
     * @param g 金矿储量
     */
    private static int getBestGoldMiningV3(int w, int[] p, int[] g) {
        // 创建当前结果
        int[] results = new int[w + 1];
        // 填充一维数组
        // 外层循环遍历金矿，因为这里的i从1开始，所以后面取值的时候，数组下标就为：i-1
        for (int i = 1; i <= g.length; i++) {
            // 内层循环遍历工人数量，倒叙遍历工人数量，当只有一个金矿的时候，
            // 无论多少个工人，开采的金矿储量也只有一个金矿的储量。
            // 金矿数量减少1，有挖和不挖两种选择，工人数在减少
            for (int j = w; j >= 1; j--) {
                // 工人数量大于当前金矿开采所需工人数量
                int resultj = 0, resultjp = 0;
                if (j >= p[i - 1]) {
                    resultj = results[j];
                    resultjp = results[j - p[i - 1]] + g[i - 1];
                    results[j] = Math.max(resultj, resultjp);
//                    results[j] = Math.max(results[j], results[j - p[i - 1]] + g[i - 1]);
                }
                System.out.println("当前金矿数量i=" + i + " ,resultj=" + resultj + " ,resultjp=" + resultjp +
                        " ,results=" + Arrays.toString(results));
            }
        }

        // 返回最后1个格子的值
        return results[w];
    }

    public static void main(String[] args) {
        // 工人数量
        int w = 10;
        // 金矿开采所需工人数量
        int[] p = {5, 5, 3, 4, 3};
        // 金矿储量
        int[] g = {400, 500, 200, 300, 350};
        System.out.println("=======================getBestGoldMining(...)================================");
        System.out.println("最优收益：" + getBestGoldMining(w, g.length, p, g));
        System.out.println("=======================getBestGoldMiningV2(...)================================");
        System.out.println("最优收益：" + getBestGoldMiningV2(w, p, g));
        System.out.println("=======================getBestGoldMiningV3(...)================================");
        System.out.println("最优收益：" + getBestGoldMiningV3(w, p, g));
    }

}
