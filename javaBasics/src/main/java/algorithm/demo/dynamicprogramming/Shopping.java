package algorithm.demo.dynamicprogramming;

/**
 * 购物
 * 淘宝的“双十一”购物节有各种促销活动，比如“满 200 元减 50 元”。假设你女朋友的购物车中有 n 个（n>100）想买的商品，
 * 她希望从里面选几个，在凑够满减条件的前提下，让选出来的商品价格总和最大程度地接近满减条件（200 元），
 * 这样就可以极大限度地“薅羊毛”。作为程序员的你，能不能编个代码来帮她搞定呢？
 *
 * @author Tang
 */
public class Shopping {

    /**
     * @param items 商品价格
     * @param n     商品个数
     * @param w     表示满减条件，比如 200
     */
    private static void double11advance(int[] items, int n, int w) {
        // 超过 3 倍就没有薅羊毛的价值了
        boolean[][] states = new boolean[n][3 * w + 1];
        // 第一行的数据要特殊处理
        states[0][0] = true;
        if (items[0] <= 3 * w) {
            states[0][items[0]] = true;
        }

        // 动态规划
        for (int i = 1; i < n; ++i) {
            // 不购买第 i 个商品
            for (int j = 0; j <= 3 * w; ++j) {
                if (states[i - 1][j]) {
                    states[i][j] = states[i - 1][j];
                }
            }
            // 购买第 i 个商品
            for (int j = 0; j <= 3 * w - items[i]; ++j) {
                if (states[i - 1][j]) {
                    states[i][j + items[i]] = true;
                }
            }
        }

        int j;
        for (j = w; j < 3 * w + 1; ++j) {
            // 输出结果大于等于 w 的最小值
            if (states[n - 1][j]) {
                break;
            }
        }
        // 没有可行解
        if (j == 3 * w + 1) {
            return;
        }
        // i 表示二维数组中的行，j 表示列
        for (int i = n - 1; i >= 1; --i) {
            if (j - items[i] >= 0 && states[i - 1][j - items[i]]) {
                // 购买这个商品
                System.out.print(items[i] + " ");
                j = j - items[i];
            } // else 没有购买这个商品，j 不变。
        }
        if (j != 0) {
            System.out.print(items[0]);
        }
    }

    public static void main(String[] args) {
        int[] items = new int[]{100, 70, 300, 20, 50, 90, 80};
        double11advance(items, 4, 200);
    }

}
