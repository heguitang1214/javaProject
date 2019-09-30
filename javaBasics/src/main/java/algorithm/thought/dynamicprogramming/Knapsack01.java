package algorithm.thought.dynamicprogramming;

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
     * 背包问题:备忘录方式解决
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

    public static void main(String[] args) {
        f(0, 0);
        System.out.println("最大容量为：" + maxW);
    }

}
