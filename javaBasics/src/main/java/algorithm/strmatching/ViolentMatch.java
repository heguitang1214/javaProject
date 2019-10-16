package algorithm.strmatching;

/**
 * 字符串的暴力匹配算法
 *
 * @author Tang
 */
public class ViolentMatch {

    /**
     * bF 匹配算法
     *
     * @param a 主串
     * @param b 模式串
     * @return 模式串在主串中匹配的起始下标
     */
    private static int bF(String a, String b) {
        int m = a.length(), n = b.length(), k;
        char[] a1 = a.toCharArray();
        char[] b1 = b.toCharArray();
        for (int i = 0; i <= m - n; i++) {
            k = 0;
            for (int j = 0; j < n; j++) {
                if (a1[i + j] == b1[j]) {
                    k++;
                } else {
                    break;
                }
            }
            if (k == n) {
                return i;
            }
        }
        return -1;
    }

    /**
     * RK匹配算法
     *
     * @param a 主串
     * @param b 模式串
     * @return 模式串在主串中匹配的起始下标
     */
    private static int rK(String a, String b) {
        int m = a.length(), n = b.length(), s, j;
        int[] hash = new int[m - n + 1];
        int[] table = new int[26];
        char[] a1 = a.toCharArray();
        char[] b1 = b.toCharArray();
        s = 1;
        //将26的次方存储在一个表里，取的时候直接用,虽然溢出，但没啥问题
        for (j = 0; j < 26; j++) {
            table[j] = s;
            s *= 26;
        }
        for (int i = 0; i <= m - n; i++) {
            s = 0;
            for (j = 0; j < n; j++) {
                s += (a1[i + j] - 'a') * table[n - 1 - j];
            }
            hash[i] = s;
        }
        s = 0;
        for (j = 0; j < n; j++) {
            s += (b1[j] - 'a') * table[n - 1 - j];
        }
        for (j = 0; j < m - n + 1; j++) {
            if (hash[j] == s) {
                return j;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        String a = "baddefabc";
        String b = "abc";
        int bfIndex = bF(a, b);
        System.out.println("BF字符串匹配算法返回匹配结果为：" + bfIndex);
        int rkIndex = rK(a, b);
        System.out.println("RK字符串匹配算法返回匹配结果为：" + rkIndex);

    }

}
