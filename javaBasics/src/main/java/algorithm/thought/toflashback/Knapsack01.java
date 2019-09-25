package algorithm.thought.toflashback;

/**
 * 0-1背包问题
 * 我们有一个背包，背包总的承载重量是 Wkg。现在我们有 n 个物品，每个物品的重量不等，并且不可分割。
 * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，如何让背包中物品的总重量最大？
 *
 * @author Tang
 */
public class Knapsack01 {

    /**
     * 存储背包中物品总重量的最大值，初始值为Integer的最小值
     */
    private static int maxW = Integer.MIN_VALUE;

    /**
     * 背包问题
     *
     * @param i     表示考察到哪个物品了
     * @param cw    表示当前已经装进去的物品的重量和
     * @param items 表示每个物品的重量
     * @param n     表示物品个数
     * @param w     背包重量
     */
    private static void knapsack(int i, int cw, int[] items, int n, int w) {
        // cw==w 表示装满了 ;i==n 表示已经考察完所有的物品
        if (cw == w || i == n) {
            if (cw > maxW) {
                // max = 当前已经装进去的物品的重量和
                // 当穷举到比当前maxW大的时候，就替换掉
                maxW = cw;
            }
            return;
        }
        // 这里，必须要考察下一次，拿到下一个数据，
        // 当前已经装进去的物品的重量和没有增加
        knapsack(i + 1, cw, items, n, w);
        // 已经超过可以背包承受的重量的时候，就不要再装了
        if (cw + items[i] <= w) {
            knapsack(i + 1, cw + items[i], items, n, w);
        }
    }

    public static void main(String[] args) {
        //假设背包可承受重量 100，物品个数 10，物品重量存储在数组 a 中
        int[] a = {11, 22, 33, 44, 50, 60, 70, 80, 90, 95};
        knapsack(0, 0, a, 10, 100);
        System.out.println("背包能装的最大容量为：" + maxW);
    }

}
