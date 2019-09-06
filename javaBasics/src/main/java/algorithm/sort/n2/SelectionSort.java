package algorithm.sort.n2;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author Tang
 */
public class SelectionSort {


    /**
     * 选择排序
     *
     * @param array 数组
     */
    private static void selectionSort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                // 找到最小的数
                if (array[j] < array[minIndex]) {
                    // 将最小数的索引保存
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 1, 9, 4, 7, 3, 8};
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }


}
