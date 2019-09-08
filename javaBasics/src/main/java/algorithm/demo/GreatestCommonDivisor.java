package algorithm.demo;

/**
 * 如何求最大公约数
 *
 * @author heguitang
 */
public class GreatestCommonDivisor {


    /**
     * 暴力枚举
     *
     * @param a 数据1
     * @param b 数据2
     * @return 最大公约数
     */
    private static int getGreatestCommonDivisor(int a, int b) {
        int big = Math.max(a, b);
        int small = Math.min(a, b);

        if (big % small == 0) {
            return small;
        }

        for (int i = small / 2; i > 1; i--) {
            if (small % i == 0 && big % i == 0) {
                return i;
            }
        }
        return 1;
    }

    /**
     * 辗转相除法
     *
     * @param a 数据1
     * @param b 数据2
     * @return 最大公约数
     */
    private static int getGreatestCommonDivisorV2(int a, int b) {
        int big = Math.max(a, b);
        int small = Math.min(a, b);

        if (big % small == 0) {
            return small;
        }

        // 两个正整数a和b(a > b)，它们的最大公约数等于a除以b的余数c和b之间的最大公约数
        return getGreatestCommonDivisorV2(big % small, small);
    }

    /**
     * 更相减损术
     *
     * @param a 数据1
     * @param b 数据2
     * @return 最大公约数
     */
    private static int getGreatestCommonDivisorV3(int a, int b) {
        int big = Math.max(a, b);
        int small = Math.min(a, b);

        if (big % small == 0) {
            return small;
        }

        // 两个正整数a和b(a > b)，它们的最大公约数等于a-b的差值c和较小数b之间的最大公约数
        return getGreatestCommonDivisorV3(big - small, small);
    }

    /**
     * 辗转相除法 + 更相减损术
     *
     * @param a 数据1
     * @param b 数据2
     * @return 最大公约数
     */
    private static int getGreatestCommonDivisorV4(int a, int b) {
        if (a == b) {
            return a;
        }

        if ((a & 1) == 0 && (b & 1) == 0) {
            // 1.如果a和b都为偶数
            return getGreatestCommonDivisorV4(a >> 1, b >> 1) << 1;
        } else if ((a & 1) == 0 && (b & 1) != 0) {
            // 2.a为偶数，b为奇数
            return getGreatestCommonDivisorV4(a >> 1, b);
        } else if ((a & 1) != 0 && (b & 1) == 0) {
            // 3.a为奇数，b为偶数
            return getGreatestCommonDivisorV4(a, b >> 1);
        } else {
            // 4.a为奇数，b为奇数
            int big = Math.max(a, b);
            int small = Math.min(a, b);
            return getGreatestCommonDivisorV4(big - small, small);
        }
    }

    public static void main(String[] args) {
        System.out.println("=====================暴力枚举法======================");
        System.out.println(getGreatestCommonDivisor(25, 5));
        System.out.println(getGreatestCommonDivisor(100, 80));
        System.out.println(getGreatestCommonDivisor(27, 14));
        System.out.println("=====================辗转相除法======================");
        System.out.println(getGreatestCommonDivisorV2(25, 5));
        System.out.println(getGreatestCommonDivisorV2(100, 80));
        System.out.println(getGreatestCommonDivisorV2(27, 14));
        System.out.println("=====================更相减损术======================");
        System.out.println(getGreatestCommonDivisorV3(25, 5));
        System.out.println(getGreatestCommonDivisorV3(100, 80));
        System.out.println(getGreatestCommonDivisorV3(27, 14));
        System.out.println("=====================辗转相除法 + 更相减损术======================");
        System.out.println(getGreatestCommonDivisorV4(25, 5));
        System.out.println(getGreatestCommonDivisorV4(100, 80));
        System.out.println(getGreatestCommonDivisorV4(27, 14));
    }


}
