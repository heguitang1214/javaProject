package algorithm.sort.nlogn;

import java.util.Arrays;

/**
 * 希尔排序
 *
 * @author Tang
 */
public class ShellSort {

    /**
     * 希尔排序
     *
     * @param array 原数组
     * @return 排序后的数组
     */
    private static int[] shellSort(int[] array) {
        int len = array.length;
        int temp;
        // 增量gap，增量为多少，就表示分为多少个组，2类似于步长
        int gap = len / 2;
        while (gap > 0) {
            // 选择一个增长序列，进行分组【preIndex + gap = i】
            for (int i = gap; i < len; i++) {
                // 临时变量，保存【希尔增量】后面的数据
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && array[preIndex] > temp) {
                    // 如果前面的元素大于后面的元素【array[preIndex] > temp】，交换数据
                    // array[i] = array[preIndex]
                    array[preIndex + gap] = array[preIndex];

                    preIndex = preIndex - gap;
                }
                array[preIndex + gap] = temp;
            }
            gap = gap / 2;
        }
        return array;
    }

    /**
     * 希尔排序
     *
     * @param array 原数组
     */
    private static void shellSortV2(int[] array) {
        // 希尔排序的增量
        int d = array.length;

        while (d > 1) {
            //使用希尔增量的方式，即每次折半
            d = d / 2;
            // 外层循环：[0,d -1]
            for (int x = 0; x < d; x++) {
                // 内层循环：[d,array.length]
                for (int i = x + d; i < array.length; i++) {
                    int temp = array[i];
                    int j;
                    // 元素分组
                    // [5, 1, 2, 3, 9, 8, 6, 7]，j=0，i=2，d=2
                    // [5, 1, 5, 3, 9, 8, 6, 7]，j=0，i=2，d=2
                    // [2, 1, 5, 3, 9, 8, 6, 7]，j=-2，d=2
                    for (j = i - d; j >= 0 && array[j] > temp; j = j - d) {
                        array[j + d] = array[j];
                    }
                    array[j + d] = temp;
                }
            }
        }
    }


    public static void main(String[] args) {
        int[] c = new int[]{5, 3, 9, 12, 6, 1, 7, 2, 4, 11, 8, 10};
        int[] d = shellSort(c);
        System.out.println(Arrays.toString(d));

//        int[] array = {5, 3, 9, 12, 6, 1, 7, 2, 4, 11, 8, 10};
        int[] array1 = {5, 8, 6, 3, 9, 1, 2, 7};
        shellSortV2(array1);
        System.out.println(Arrays.toString(array1));
    }

}
