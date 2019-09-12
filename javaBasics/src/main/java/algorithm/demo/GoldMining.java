package algorithm.demo;

/**
 * 求解金矿问题
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
     * 获得金矿最优收益
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
        for (int i = 1; i <= g.length; i++) {
            for (int j = w; j >= 1; j--) {
                if (j >= p[i - 1]) {
                    results[j] = Math.max(
                            results[j],
                            results[j - p[i - 1]] + g[i - 1]);
                }
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
