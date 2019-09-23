package algorithm.strmatching;

import java.util.Arrays;

/**
 * 字符串匹配算法：KMP
 *
 * @author Tang
 */
public class KnuthMorrisPratt {


    /**
     * KMP字符串匹配算法
     *
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     * @return 返回主串与模式串第一个匹配的字符的位置
     */
    public static int kmp(char[] a, int n, char[] b, int m) {
        int[] next = getNexts(b, m);
        int j = 0;
        // 循环主串
        for (int i = 0; i < n; ++i) {
            // 一直找到 a[i] 和 b[j]
            while (j > 0 && a[i] != b[j]) {
                // 可匹配前缀子串的结尾字符下标
                j = next[j - 1] + 1;
            }
            // 主串和模式串相匹配，j++
            if (a[i] == b[j]) {
                ++j;
            }
            // 找到匹配模式串的了
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }


    /**
     * 失效函数：保存的是模式串的【最长前缀子串】
     *
     * @param b 模式串
     * @param m 模式串的长度
     * @return 失效函数
     */
    private static int[] getNexts(char[] b, int m) {
        int[] next = new int[m];
        next[0] = -1;
        // 最长可以匹配前缀子串的结尾字符下标，即：可以匹配的字符长度
        int k = -1;
        // 从前往后循环模式串
        for (int i = 1; i < m; ++i) {
            while (k != -1 && b[k + 1] != b[i]) {
                //4. 遇到不能匹配的数据， 例如：b[2] != b[5]
                k = next[k];
            }
            // 1.有字符匹配上，例如 b[0] = b[3]
            // 3.继续比较，例如 b[1] = b[4]
            if (b[k + 1] == b[i]) {
                // 结束字符++
                ++k;
            }
            // 2.更新【前缀结尾字符下标】 对应的 【可匹配前缀子串的结尾字符下标】
            // 如：next[3] = 0
            next[i] = k;
        }
        System.out.println("失效函数next为：" + Arrays.toString(next));
        return next;
    }

    public static void main(String[] args) {
        char[] a = {'a', 'b', 'c', 'a', 'c', 'a', 'b', 'd', 'c'};
        char[] b = {'a', 'c', 'a'};
        int index = kmp(a, a.length, b, b.length);
        System.out.println(index);

        char[] c = {'a', 'b', 'c', 'a', 'c', 'a', 'b', 'd', 'c'};
        char[] d = {'a', 'b', 'a', 'b', 'a', 'c', 'd'};
        int index2 = kmp(c, c.length, d, d.length);
        System.out.println(index2);

//        char[] d = {'a', 'b', 'a', 'b', 'a', 'c', 'd'};
//        System.out.println(Arrays.toString(getNexts(d, d.length)));

    }

}
