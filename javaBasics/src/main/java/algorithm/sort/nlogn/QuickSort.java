package algorithm.sort.nlogn;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author Tang
 */
public class QuickSort {


    /**
     * 快速排序
     *
     * @param array 原数组
     */
    private static void quickSort(int[] array) {
        quickSortInternally(array, 0, array.length - 1);
    }

    /**
     * 快速排序递归函数
     *
     * @param array 数组
     * @param p     数组起始下标
     * @param r     数组结束下标
     */
    private static void quickSortInternally(int[] array, int p, int r) {
        if (p >= r) {
            return;
        }
        // 获取分区点，然后递归分区
        int q = partition(array, p, r);

        quickSortInternally(array, p, q - 1);
        quickSortInternally(array, q + 1, r);
    }

    /**
     * 快排分区
     *
     * @param array 数组
     * @param p     数组起始下标
     * @param r     数组结束下标
     * @return 分区点
     */
    private static int partition(int[] array, int p, int r) {
        int pivot = array[r];
        // i：大于分区点元素的界限
        int i = p;

        // j：在数组中查找数据
        for (int j = p; j < r; j++) {
            // 当前元素小于分区点的元素
            if (array[j] < pivot) {
                if (i == j) {
                    ++i;
                } else {
                    // 当前元素array[j]小于分区点的元素，和i交换位置
                    // 并且i向后移动一位（i是大于分区点的界限）
                    int temp = array[i];
                    array[i++] = array[j];
                    array[j] = temp;
                }
            }
        }

        // array[j]循环遍历完，交换最后的元素
        int temp = array[i];
        // array[i] = pivot;
        array[i] = array[r];
        array[r] = temp;

        System.out.println("i=" + i);
        // i的位置就是现在分区点的位置，
        // 左边的数据小于分区点的数据，右边的数据大于分区点的数据
        return i;
    }


    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 9, 8};
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }

}
