package algorithm.demo;

/**
 * 寻找缺失的整数
 * (1)在一个无序数组里有99个不重复的正整数，范围是 1~100，唯独缺少1个1~100中的整数，如何找到这个确实的整数？
 * (2)在一个无序数组里有若干个正整数，范围是1 ~100，其中 99 个整数出现了偶数次，只有 1 个整数出现了奇数次，
 * 如何找到这个出现奇数次的整数？
 * (3)在一个无序数组里有若干个正整数，范围是1 ~100，其中 99 个整数出现了偶数次，只有 2 个整数出现了奇数次，
 * 如何找到这两个出现奇数次的整数？
 *
 * @author heguitang
 */
public class FindLostNum {

    /**
     * 寻找缺失的整数
     *
     * @param array 存储数据的数组
     */
    private static int[] findLostNum(int[] array) {
        // 用于存储两个出现奇数次的整数
        int result[] = new int[2];
        //第一次整体异或
        int xorResult = 0;
        for (int i = 0; i < array.length; i++) {
            xorResult ^= array[i];
        }
        //如果异或结果为0，说明输入数组不符合题目
        if (xorResult == 0) {
            return null;
        }

        // 确定2个整数的不同位，以此来做分组
        int separator = 1;
        while (0 == (xorResult & separator)) {
            separator <<= 1;
        }

        //第2次分组异或
        for (int i = 0; i < array.length; i++) {
            if (0 == (array[i] & separator)) {
                result[0] ^= array[i];
            } else {
                result[1] ^= array[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {4, 1, 2, 2, 5, 1, 4, 3};
        int[] result = findLostNum(array);
        System.out.println(result[0] + "," + result[1]);
    }

}
