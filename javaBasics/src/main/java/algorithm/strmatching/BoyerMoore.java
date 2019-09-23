package algorithm.strmatching;

import java.util.Arrays;

/**
 * BM字符串匹配
 *
 * @author Tang
 */
public class BoyerMoore {

    /**
     * 全局变量或成员变量
     */
    private static final int SIZE = 256;

    /**
     * BM坏字符匹配
     *
     * @param mainStr    主串
     * @param n          主串长度
     * @param patternStr 模式串
     * @param m          模式串长度
     * @return 返回主串与模式串第一个匹配的字符的位置
     */
    private static int bm(char[] mainStr, int n, char[] patternStr, int m) {
        // 记录模式串中每个字符最后出现的位置
        int[] bc = new int[SIZE];
        // 构建坏字符哈希表
        generateBC(patternStr, m, bc);
        // i 表示主串与模式串对齐的第一个字符
        int i = 0;
        while (i <= n - m) {
            int j;
            // 模式串从后往前匹配
            for (j = m - 1; j >= 0; --j) {
                // 坏字符对应模式串中的下标是 j
                // 找到坏字符，j是模式串的下表，i + j 对应着主串的下标
                if (mainStr[i + j] != patternStr[j]) {
                    break;
                }
            }
            // 在i <= n - m 范围内，找不到坏字符，说明匹配成功
            if (j < 0) {
                // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                return i;
            }
            // 这里等同于将模式串往后滑动 j-bc[(int)a[i+j]] 位
            // 坐标i向后移动
            // bc[(int) mainStr[i + j]]：在hash表中查找坏字符在模式串中存不存在
            i = i + (j - bc[(int) mainStr[i + j]]);
        }
        return -1;
    }


    /**
     * BM坏字符匹配:构建坏字符哈希表
     *
     * @param patternStr 模式串
     * @param m          模式串的长度
     * @param bc         散列表
     */
    private static void generateBC(char[] patternStr, int m, int[] bc) {
        for (int i = 0; i < SIZE; ++i) {
            // 初始化 bc
            bc[i] = -1;
        }
        for (int i = 0; i < m; ++i) {
            // 计算 b[i] 的 ASCII 值
            int ascii = (int) patternStr[i];
            bc[ascii] = i;
        }
        System.out.println("构建的HASH表为：" + Arrays.toString(bc));
    }


    /**
     * 构建suffix数组和prefix数组
     *
     * @param b      模式串
     * @param m      模式串长度
     * @param suffix 在模式串中，查找跟好后缀匹配的另一个子串
     * @param prefix 记录模式串的后缀子串是否能匹配模式串的前缀子串
     */
    private static void generateGS(char[] b, int m, int[] suffix, boolean[] prefix) {
        // 初始化
        for (int i = 0; i < m; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }

        // b[0, i]，遍历模式串
        for (int i = 0; i < m - 1; ++i) {
            int j = i;
            // 公共后缀子串长度
            int k = 0;
            // 与 b[0, m-1] 求公共后缀子串
            // 因为j的初始值等于i，j--向前移动【j -> 0】，后缀子串向前移动，k++【b[m-1-k]】，
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                // 从后往前匹配
                --j;
                ++k;
                //j+1 表示公共后缀子串在 b[0, i] 中的起始下标
                suffix[k] = j + 1;
            }
            // 如果公共后缀子串也是模式串的前缀子串
            if (j == -1) {
                prefix[k] = true;
            }
        }
    }


    /**
     * BM字符串匹配： 好后缀 + 坏字符
     *
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return 返回主串与模式串第一个匹配的字符的位置
     */
    private static int bmV2(char[] a, int n, char[] b, int m) {
        // 记录模式串中每个字符最后出现的位置
        int[] bc = new int[SIZE];
        // 构建坏字符哈希表
        generateBC(b, m, bc);

        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        generateGS(b, m, suffix, prefix);

        // j 表示主串与模式串匹配的第一个字符
        int i = 0;
        // 循环主串
        while (i <= n - m) {
            int j;
            // 模式串从后往前匹配
            for (j = m - 1; j >= 0; --j) {
                // 坏字符对应模式串中的下标是 j
                if (a[i + j] != b[j]) {
                    break;
                }
            }
            if (j < 0) {
                // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                return i;
            }

            // 好后缀的处理
            // 坏字符滑动的位数
            int x = j - bc[(int) a[i + j]];
            int y = 0;
            // 如果有好后缀的话，只要坏字符不是模式串的最后一个字符，那么久存在好后缀
            if (j < m - 1) {
                // 好后缀滑动的位数
                y = moveByGS(j, m, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }


    /**
     * 模式串向后滑动的位数
     *
     * @param j      坏字符对应的模式串中的字符下标
     * @param m      模式串长度
     * @param suffix suffix数组
     * @param prefix prefix数组
     * @return 滑动的位数
     */
    private static int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        // 好后缀长度
        int k = m - 1 - j;
        // suffix 数组中有匹配的子串：在模式串中，查找跟好后缀匹配的另一个子串
        if (suffix[k] != -1) {
            return j - suffix[k] + 1;
        }
        // prefix数组：记录模式串的后缀子串是否能匹配模式串的前缀子串
        // j：坏字符下表，j + 1：好后缀初始下标，j + 2：好后缀后缀子串的起始下标
        for (int r = j + 2; r <= m - 1; ++r) {
            if (prefix[m - r] == true) {
                return r;
            }
        }
        // 移动整个模式串
        return m;
    }


    public static void main(String[] args) {
        char[] a = {'a', 'b', 'c', 'a', 'c', 'a', 'b', 'd', 'c'};
        char[] b = {'a', 'c', 'a'};
        int index = bm(a, a.length, b, b.length);
        System.out.println(index);

        char[] c = {'a', 'b', 'c', 'a', 'c', 'a', 'b', 'd', 'c'};
        char[] d = {'a', 'b', 'd'};
        int index2 = bm(c, c.length, d, d.length);
        System.out.println(index2);

        System.out.println("========================坏字符+好后缀===============================");
        int index1 = bmV2(a, a.length, b, b.length);
        System.out.println(index1);

        int index4 = bmV2(c, c.length, d, d.length);
        System.out.println(index4);
    }

}
