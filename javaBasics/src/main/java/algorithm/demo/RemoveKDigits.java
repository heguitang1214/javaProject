package algorithm.demo;

/**
 * 删除K个数字后的最小值
 *
 * @author heguitang
 */
public class RemoveKDigits {

    /**
     * 删除整数的k个数字，获得删除后的最小值
     *
     * @param num 原整数
     * @param k   删除数量
     * @return 删除后的最小值
     */
    private static String removeKDigits(String num, int k) {
        String numnew = num;
        for (int i = 0; i < k; i++) {
            boolean hasCut = false;
            // 从左向右遍历，找到比自己右侧数字大的数字并删除
            // 这里j < numnew.length() - 1，是因为 j 和 j + 1做的比较
            for (int j = 0; j < numnew.length() - 1; j++) {
                // 如果前一位大于后一位
                if (numnew.charAt(j) > numnew.charAt(j + 1)) {
                    numnew = numnew.substring(0, j) + numnew.substring(j + 1, numnew.length());
                    hasCut = true;
                    break;
                }
            }
            //如果没有找到要删除的数字，则删除最后一个数字
            if (!hasCut) {
                numnew = numnew.substring(0, numnew.length() - 1);
            }
            //清除整数左侧的数字0
            numnew = removeZero(numnew);
        }

        //如果整数的所有数字都被删除了，直接返回0
        if (num.length() == 0) {
            return "0";
        }
        return numnew;
    }

    /**
     * 清除左侧的数字0
     *
     * @param num 原数字
     * @return 删除左侧0后的数字
     */
    private static String removeZero(String num) {
        int start = 0;
        for (int j = 0; j < num.length() - 1; j++) {
            if (num.charAt(j) != '0') {
                break;
            }
            start++;
        }
        num = num.substring(start, num.length());
        return num;
    }


    /**
     * 删除整数的k个数字，获得删除后的最小值
     *
     * @param num 原整数
     * @param k   删除数量
     * @return 删除后的最小值
     */
    private static String removeKDigitsV2(String num, int k) {
        //新整数的最终长度 = 原整数长度 - k
        int newLength = num.length() - k;
        //创建一个栈，用于接收所有的数字
        char[] stack = new char[num.length()];

        int top = 0;
        for (int i = 0; i < num.length(); i++) {
            // 遍历当前数字
            char c = num.charAt(i);
            //当栈顶数字大于遍历到的当前数字，栈顶数字出栈（相当于删除数字）
            while (top > 0 && k > 0 && stack[top - 1] > c) {
                // 删除栈顶的数据，数组下标前移
                top--;
                k--;
            }

            //如果遇到数字0，且栈为空，0不入栈
            if ('0' == c && top == 0) {
                // 0开头的数字，去掉0，如：012 == > 12
                newLength--;
                if (newLength <= 0) {
                    return "0";
                }
                continue;
            }
            // 遍历到的当前数字入栈
            stack[top++] = c;
        }
        // 用栈构建新的整数字符串
        return newLength <= 0 ? "0" : new String(stack, 0, newLength);
    }


    public static void main(String[] args) {
        System.out.println("========================removeKDigits(...)============================");
        System.out.println(removeKDigits("120", 1));
        System.out.println(removeKDigits("1593212", 3));
        System.out.println(removeKDigits("1593212", 3));
        System.out.println(removeKDigits("30200", 1));
        System.out.println(removeKDigits("10", 2));
        System.out.println(removeKDigits("541270936", 3));
        System.out.println(removeKDigits("1593212", 4));
        System.out.println(removeKDigits("1000020000000010", 2));

        System.out.println("========================removeKDigitsV2(...)============================");
        System.out.println(removeKDigitsV2("120", 1));
        System.out.println(removeKDigitsV2("1593212", 3));
        System.out.println(removeKDigitsV2("1593212", 3));
        System.out.println(removeKDigitsV2("30200", 1));
        System.out.println(removeKDigitsV2("10", 2));
        System.out.println(removeKDigitsV2("541270936", 3));
        System.out.println(removeKDigitsV2("1593212", 4));
        System.out.println(removeKDigitsV2("1000020000000010", 2));
    }

}
