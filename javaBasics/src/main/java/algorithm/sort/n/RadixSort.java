package algorithm.sort.n;

import java.util.Arrays;

/**
 * 基数排序：排序数字
 *
 * @author Tang
 */
public class RadixSort {

    /**
     * 基数排序
     */
    private static void radixSort(int[] array) {
        // 1.找到最大值，是为了确定要比较多少次，例如：2、32就需要分别排序2次
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 2.从个位开始，对数组arr按"指数"进行排序
        // exp 分别代表的是个位，十位，百位......，依次排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(array, exp);
        }

    }

    /**
     * 计数排序-对数组按照"某个位数"进行排序
     *
     * @param arr 数组
     * @param exp 指数
     */
    private static void countingSort(int[] arr, int exp) {
        if (arr.length <= 1) {
            return;
        }

        // (1)计算每个元素的个数，0-9
        int[] count = new int[10];
        for (int i = 0; i < arr.length; i++) {
            // 获取对应“指数”的元素，然后排序
            int index = (arr[i] / exp) % 10;
            count[index]++;
        }

        // (2)计算排序后的位置，即处理count数组
        for (int i = 1; i < count.length; i++) {
            // 原地计数排序
            count[i] += count[i - 1];
        }

        // (3)临时数组r，存储排序之后的结果
        int[] result = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            // 得到arr[i]在count数组中对应的下标
            int countNumber = count[(arr[i] / exp) % 10];
            result[countNumber - 1] = arr[i];
            // countNumber中的数据--
            count[(arr[i] / exp) % 10]--;
        }

        // (4)将排序后的数组，赋值给原数组
        for (int i = 0; i < arr.length; i++) {
            arr[i] = result[i];
        }
    }


    public static void main(String[] args) {
        int[] array1 = new int[]{2, 3, 6, 8, 4};
        radixSort(array1);
        System.out.println(Arrays.toString(array1));

        int[] array2 = new int[]{12, 3, 216, 8, 4};
        radixSort(array2);
        System.out.println(Arrays.toString(array2));


        int[] array = new int[]{1234567896, 1234567897, 1234567891, 1234567893, 1234567892};
        radixSort(array);
        System.out.println(Arrays.toString(array));


    }

}
