package algorithm.sort.n;

import java.util.Arrays;

/**
 * 计数排序
 *
 * @author Tang
 */
public class CountingSort {


    /**
     * 计数排序
     *
     * @param array 待排序数组
     */
    private static int[] countingSort(int[] array) {
        // 1.得到数列的最大值和最小值，并算出差值d
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        int d = max - min;

        // 2.创建【统计数组】并统计对应元素的个数
        int[] countArray = new int[d + 1];
        for (int i = 0; i < array.length; i++) {
            countArray[array[i] - min]++;
        }

        // 3.统计数组做变形，后面的元素等于前面元素之和--【原地排序】
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] = countArray[i] + countArray[i - 1];
        }

        // 4.倒序遍历原始数组，从统计数组找到正确的位置，输出到结果数组
        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            int countArrayIndex = countArray[array[i] - min] - 1;
            sortedArray[countArrayIndex] = array[i];
            countArray[array[i] - min]--;
        }

        return sortedArray;
    }


    public static void main(String[] args) {
        int[] array = new int[]{95, 94, 91, 98, 99, 90, 99, 93, 91, 92};
        int[] sortedArray = countingSort(array);
        System.out.println(Arrays.toString(sortedArray));
    }

}
