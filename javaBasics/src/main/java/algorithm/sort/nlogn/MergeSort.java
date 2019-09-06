package algorithm.sort.nlogn;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author Tang
 */
public class MergeSort {

    /**
     * 归并排序
     *
     * @param array 待排序数组
     */
    private static void mergeSort(int[] array) {
        if (array.length < 2) {
            return;
        }
        // 在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        int[] temp = new int[array.length];
        mergeSort(array, 0, array.length - 1, temp);
    }

    /**
     * @param array 数组
     * @param left  数组左下标
     * @param right 数组右下标
     * @param temp  临时数组空间
     */
    private static void mergeSort(int[] array, int left, int right, int[] temp) {
        // 分治思想
        if (left < right) {
            int mid = (left + right) / 2;
            // 左边归并排序，使得左子序列有序，因为递归后，数组中只有一个元素
            mergeSort(array, left, mid, temp);
            // 右边归并排序，使得右子序列有序
            mergeSort(array, mid + 1, right, temp);
            merge(array, left, mid, right, temp);
        }
    }

    /**
     * 归并排序：将两段排序好的数组结合成一个排序数组
     *
     * @param array 原数组
     * @param left  数组左下标
     * @param mid   数组中间下表
     * @param right 数组右下标
     * @param temp  临时数组空间
     */
    private static void merge(int[] array, int left, int mid, int right, int[] temp) {
        //左序列指针
        int i = left;
        //右序列指针
        int j = mid + 1;
        //临时数组指针
        int t = 0;
        while (i <= mid && j <= right) {
            if (array[i] < array[j]) {
                temp[t++] = array[i++];
            } else {
                temp[t++] = array[j++];
            }
        }
        //将左边剩余元素填充进temp中
        while (i <= mid) {
            temp[t++] = array[i++];
        }
        while (j <= right) {
            temp[t++] = array[j++];
        }

        //将temp中的元素全部拷贝到原数组中
        t = 0;
        while (left <= right) {
            array[left++] = temp[t++];
        }
    }


    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));

        int[] c = new int[]{1, 2, 3, 4, 9, 8};
        mergeSort(c);
        System.out.println(Arrays.toString(c));
    }

}
