package algorithm.demo;

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


        return numbers;
    }

    /**
     * 查找给定数组的逆序区域的前一位下标
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
        return 0;
    }


    public static void main(String[] args) {

    }

}
