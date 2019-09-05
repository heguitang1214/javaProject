package algorithm.sort.n2;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @author Tang
 */
public class InsertionSort {


    /**
     * 插入排序
     *
     * @param arr  数组
     * @param size 数组大小
     */
    private static void insertionSort(int[] arr, int size) {
        if (size <= 1) {
            return;
        }

        // 默认第一个元素有序，所以i从 1 开始
        for (int i = 1; i < size; i++) {
            int value = arr[i];
            int j = i - 1;
            // 查找插入的位置，这里是从后往前找，所以当value小于排序区间arr[j]的时候，就只需要循环将
            // arr[j]往后移动一位，然后把value放到arr[j+1]的位置即可
            for (; j >= 0; j--) {
                if (arr[j] > value) {
                    // 数据移动：当前值是value，拿value和已排序区间[0,j]数据进行对比
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            // 插入数据，因为前面arr[j] > value判断成立后，j就--了，所以这里需要重新 + 回来
            arr[j + 1] = value;
        }
    }


    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 1, 9, 4, 7, 3,};
        insertionSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));
    }


}
