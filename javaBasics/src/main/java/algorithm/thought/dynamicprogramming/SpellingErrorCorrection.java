package algorithm.thought.dynamicprogramming;

/**
 * 拼写纠错
 *
 * @author Tang
 */
public class SpellingErrorCorrection {


    private static char[] a = "mitcmu".toCharArray();
    private static char[] b = "mtacnu".toCharArray();

    /**
     * 字符数组a的长度
     */
    private static int n = a.length;
    private static int m = b.length;

    /**
     * 存储结果
     */
    private static int minDist = Integer.MAX_VALUE;

    /**
     * 递归实现莱文斯坦距离：删除、添加、替换三种操作，表示【两个字符串差异的大小】
     * 调用方式 lwstBT(0, 0, 0);
     *
     * @param i     字符a的起始位置
     * @param j     字符b的起始位置
     * @param edist 编辑距离
     */
    private static void lwstBT(int i, int j, int edist) {
        // 其中的一个字符考察完毕，edist存储的就是【两个字符串差异的大小】
        if (i == n || j == m) {
            if (i < n) {
                edist += (n - i);
            }
            if (j < m) {
                edist += (m - j);
            }
            if (edist < minDist) {
                minDist = edist;
            }
            return;
        }

        // 两个字符匹配
        if (a[i] == b[j]) {
            lwstBT(i + 1, j + 1, edist);
            // 两个字符不匹配
        } else {
            // 删除 a[i] 或者 b[j] 前添加一个字符
            lwstBT(i + 1, j, edist + 1);
            // 删除 b[j] 或者 a[i] 前添加一个字符
            lwstBT(i, j + 1, edist + 1);
            // 将 a[i] 和 b[j] 替换为相同字符
            lwstBT(i + 1, j + 1, edist + 1);
        }
    }

    /**
     * 动态规划实现莱文斯坦距离：删除、添加、替换三种操作，表示【两个字符串差异的大小】
     *
     * @param a 字符串数组a
     * @param n 字符串数组a的长度
     * @param b 字符串数组b
     * @param m 字符串数组b的长度
     * @return 莱文斯坦距离
     */
    private static int lwstDP(char[] a, int n, char[] b, int m) {
        int[][] minDist = new int[n][m];
        // 初始化第 0 行:a[0..0] 与 b[0..j] 的编辑距离
        for (int j = 0; j < m; ++j) {
            if (a[0] == b[j]) {
                minDist[0][j] = j;
            } else if (j != 0) {
                // min_edist(i,j-1)+1
                minDist[0][j] = minDist[0][j - 1] + 1;
            } else {
                // a[0] != b[0]的时候，初始值为1
                minDist[0][j] = 1;
            }
        }
        // 初始化第 0 列:a[0..i] 与 b[0..0] 的编辑距离
        for (int i = 0; i < n; ++i) {
            // 当a[i] == b[0]的时候，如：m匹配mitcmu的时候，如果要匹配到第二个m，那么得删除mitc，即编辑距离为4
            if (a[i] == b[0]) {
                minDist[i][0] = i;
            } else if (i != 0) {
                minDist[i][0] = minDist[i - 1][0] + 1;
            } else {
                minDist[i][0] = 1;
            }
        }

        // 按行填表
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < m; ++j) {
                if (a[i] == b[j]) {
                    // 状态转移方程
                    minDist[i][j] = min(minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1]);
                } else {
                    minDist[i][j] = min(minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1] + 1);
                }
            }
        }
        return minDist[n - 1][m - 1];
    }

    private static int min(int x, int y, int z) {
        int minv = Integer.MAX_VALUE;
        if (x < minv) {
            minv = x;
        }
        if (y < minv) {
            minv = y;
        }
        if (z < minv) {
            minv = z;
        }
        return minv;
    }

    /**
     * 动态规划实现计算最长公共子串长度：表示两个字符串相似程度的大小
     *
     * @param a 字符串数组a
     * @param n 字符串数组a的长度
     * @param b 字符串数组b
     * @param m 字符串数组b的长度
     * @return 最长公共子串长度
     */
    private static int lcs(char[] a, int n, char[] b, int m) {
        int[][] maxlcs = new int[n][m];
        // 初始化第 0 行：a[0..0] 与 b[0..j] 的 maxlcs
        for (int j = 0; j < m; ++j) {
            if (a[0] == b[j]) {
                maxlcs[0][j] = 1;
            } else if (j != 0) {
                maxlcs[0][j] = maxlcs[0][j - 1];
            } else {
                maxlcs[0][j] = 0;
            }
        }

        // 初始化第 0 列：a[0..i] 与 b[0..0] 的 maxlcs
        for (int i = 0; i < n; ++i) {
            if (a[i] == b[0]) {
                maxlcs[i][0] = 1;
            } else if (i != 0) {
                maxlcs[i][0] = maxlcs[i - 1][0];
            } else {
                maxlcs[i][0] = 0;
            }
        }

        // 填表
        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < m; ++j) {
                if (a[i] == b[j]) {
                    maxlcs[i][j] = max(maxlcs[i - 1][j], maxlcs[i][j - 1], maxlcs[i - 1][j - 1] + 1);
                } else {
                    maxlcs[i][j] = max(maxlcs[i - 1][j], maxlcs[i][j - 1], maxlcs[i - 1][j - 1]);
                }
            }
        }
        return maxlcs[n - 1][m - 1];
    }

    private static int max(int x, int y, int z) {
        int maxv = Integer.MIN_VALUE;
        if (x > maxv) {
            maxv = x;
        }
        if (y > maxv) {
            maxv = y;
        }
        if (z > maxv) {
            maxv = z;
        }
        return maxv;
    }


    public static void main(String[] args) {
        lwstBT(0, 0, 0);
        System.out.println("递归实现，莱文斯坦距离为：" + minDist);
        int lwstDp = lwstDP(a, a.length, b, b.length);
        System.out.println("动态规划实现，莱文斯坦距离为：" + lwstDp);
        int lcs = lcs(a, a.length, b, b.length);
        System.out.println("动态规划实现，最长公共子串长度为：" + lcs);

    }

}
