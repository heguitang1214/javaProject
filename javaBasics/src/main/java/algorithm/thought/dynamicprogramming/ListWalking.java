package algorithm.thought.dynamicprogramming;

/**
 * 列表走法
 * 假设我们有一个 n 乘以 n 的矩阵 w[n][n]。矩阵存储的都是正整数。棋子起始位置在左上角，终止位置在右下角。
 * 我们将棋子从左上角移动到右下角。每次只能向右或者向下移动一位。从左上角到右下角，会有很多不同的路径可以走。
 * 我们把每条路径经过的数字加起来看作路径的长度。那从左上角移动到右下角的最短路径长度是多少呢？
 *
 * @author Tang
 */
public class ListWalking {

    /**
     * 全局变量或者成员变量
     */
    private static int minDist = Integer.MAX_VALUE;


    /**
     * 调用方式：minDistBacktracing(0, 0, 0, w, n);
     *
     * @param i    行坐标
     * @param j    列坐标
     * @param dist 从起点到达 (i, j) 的路径长度
     * @param w    矩阵
     * @param n    目标坐标
     */
    private static void minDistBacktracing(int i, int j, int dist, int[][] w, int n) {
        // 到达了 n-1, n-1 这个位置了
        if (i == n && j == n) {
            if (dist < minDist) {
                minDist = dist;
            }
            return;
        }
        // 往下走，更新 i=i+1, j=j
        if (i < n) {
            minDistBacktracing(i + 1, j, dist + w[i][j], w, n);
        }
        // 往右走，更新 i=i, j=j+1
        if (j < n) {
            minDistBacktracing(i, j + 1, dist + w[i][j], w, n);
        }
    }


    /**
     * @param matrix 矩阵
     * @param n      目标坐标
     * @return 最短路径长度
     */
    private static int minDistDp(int[][] matrix, int n) {
        int[][] states = new int[n][n];
        int sum = 0;
        // 初始化 states 的第一行数据
        for (int j = 0; j < n; ++j) {
            sum += matrix[0][j];
            states[0][j] = sum;
        }

        sum = 0;
        // 初始化 states 的第一列数据
        for (int i = 0; i < n; ++i) {
            sum += matrix[i][0];
            states[i][0] = sum;
        }

        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                states[i][j] =
                        matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }
        return states[n - 1][n - 1];
    }

    /**
     * 源矩阵
     */
    private static int[][] matrix = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
    private static int n = 4;
    private static int[][] mem = new int[4][4];

    /**
     * 调用 minDist(n-1, n-1);
     *
     * @param i 行
     * @param j 列
     * @return 最短路径
     */
    private static int minDist(int i, int j) {
        if (i == 0 && j == 0) {
            return matrix[0][0];
        }
        if (mem[i][j] > 0) {
            return mem[i][j];
        }
        int minLeft = Integer.MAX_VALUE;
        if (j - 1 >= 0) {
            minLeft = minDist(i, j - 1);
        }
        int minUp = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            minUp = minDist(i - 1, j);
        }

        int currMinDist = matrix[i][j] + Math.min(minLeft, minUp);
        mem[i][j] = currMinDist;
        return currMinDist;
    }


    public static void main(String[] args) {
        minDistBacktracing(0, 0, 0, matrix, 3);
        System.out.println(minDist);

        int result = minDistDp(matrix, 3);
        System.out.println(result);

        int minDist = minDist(n - 1, n - 1);
        System.out.println("矩阵最短路径是：" + minDist);
    }
}
