package algorithm.demo;

import java.util.Arrays;

/**
 * 寻找全排序的下一个数
 * 在一个整数所包含数字的组合中，找到一个大于且仅大于原数的新整数
 *
 * @author heguitang
 */
public class FindNearestNumber {


    private static int[] findNearestNumber(int[] numbers) {
        //1.从后向前查看逆序区域，找到逆序区域的前一位，也就是数字置换的边界
        int index = findTransferPoint(numbers);
        //如果数字置换边界是0，说明整个数组已经逆序，无法得到更大的相同数字组成的整数，返回null
        if (index == 0) {
            return null;
        }

        //2.把逆序区域的前一位和逆序区域中刚刚大于它的数字交换位置
        //拷贝入参，避免直接修改入参
        int[] numbersCopy = Arrays.copyOf(numbers, numbers.length);
        exchangeHead(numbersCopy, index);

        //3.把原来的逆序区域转为顺序
        reverse(numbersCopy, index);
        return numbersCopy;
    }

    /**
     * 查找给定数组的逆序区域的下标
     *
     * @param numbers 给定数组
     * @return 下标
     */
    private static int findTransferPoint(int[] numbers) {
        for (int i = numbers.length - 1; i > 0; i--) {
            if (numbers[i] > numbers[i - 1]) {
                return i;
            }
        }
        // 返回0表示是整个数组已经逆序
        return 0;
    }

    /**
     * 交换逆序区域前一位的数字与逆序区域最小的数子(最后一位)，所以从后往前遍历
     *
     * @param numbers 数组
     * @param index   给定数组的逆序区域的下标
     * @return 交换数据的数组
     */
    private static int[] exchangeHead(int[] numbers, int index) {
        // 给定数组的逆序区域的前一位数
        int head = numbers[index - 1];
        for (int i = numbers.length - 1; i > 0; i--) {
            // 逆序区域本来就是最大数的组合，所以要从 index - 1 进行变换
            if (head < numbers[i]) {
                numbers[index - 1] = numbers[i];
                numbers[i] = head;
                break;
            }
        }
        return numbers;
    }


    /**
     * 把逆序区域转换为顺序区域
     *
     * @param num   交换元素后的数组
     * @param index 给定数组的逆序区域的下标
     */
    private static void reverse(int[] num, int index) {
        for (int i = index, j = num.length - 1; i < j; i++, j--) {
            // 互换首位元素的位置，如：54321 ==> 12345
            int temp = num[i];
            num[i] = num[j];
            num[j] = temp;
        }
    }

    /**
     * 打印输出数组为整数
     *
     * @param numbers 数组
     */
    private static void outputNumbers(int[] numbers) {
        for (int i : numbers) {
            System.out.print(i);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        //打印12345之后的10个全排列整数
        for (int i = 0; i < 10; i++) {
            numbers = findNearestNumber(numbers);
            outputNumbers(numbers);
        }
    }


}
