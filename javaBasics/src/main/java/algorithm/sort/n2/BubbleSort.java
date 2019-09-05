package algorithm.sort.n2;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author Tang
 */
public class BubbleSort {


    /**
     * 冒泡排序
     *
     * @param arr  数组
     * @param size 数组大小
     */
    private static void bubbleSort(int[] arr, int size) {
        if (size <= 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            // 提前退出冒泡排序的标志位
            boolean flag = false;
            // j < size - i - 1 是因为第i轮的元素个数n(size - i)个元素只需要对比n-1次
            for (int j = 0; j < size - i - 1; j++) {
                // 交换
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            // 没有数据交换，表示后面的数据已经有序，提前退出
            if (!flag) {
                break;
            }
        }
    }


    /**
     * 两个数进行数据交换
     *
     * @param a 数据1
     * @param b 数据2
     */
    private static void dataExchange(int a, int b) {
        System.out.println("交换前的数据为：a=" + a + "，b=" + b);
        a = a + b;
        // b = (a + b) -b = a
        b = a - b;
        // a = (a + b) - b(这个b的值已经是a了) = b
        a = a - b;
        System.out.println("交换后的数据为：a=" + a + "，b=" + b);
    }


    /**
     * 两个数进行数据交换
     *
     * @param a 数据1
     * @param b 数据2
     */
    private static void dataExchange1(int a, int b) {
        System.out.println("交换前的数据为：a=" + a + "，b=" + b);
        a = a ^ b;
        // b = (a ^ b) ^ b = a
        b = a ^ b;
        // a = (a ^ b) ^ b(这个b的值已经是a了) = b
        a = a ^ b;
        System.out.println("交换后的数据为：a=" + a + "，b=" + b);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 5, 3, 6, 9, 1, 2};
        bubbleSort(arr, arr.length);
        System.out.println(Arrays.toString(arr));


        int a = 1, b = 2, aa = 3, bb = 4;
        dataExchange(a, b);
        dataExchange(aa, bb);
        System.out.println("=====================================");
        dataExchange1(a, b);
        dataExchange1(aa, bb);

    }


}
