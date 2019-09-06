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


    public static void main(String[] args) {
        int[] c = new int[]{1, 7, 2, 3, 4, 9, 8, 5};
        int[] d = shellSort(c);
        System.out.println(Arrays.toString(d));
    }

}
