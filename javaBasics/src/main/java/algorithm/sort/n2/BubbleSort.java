package algorithm.sort.n2;

import java.util.Arrays;

/**
 * 冒泡排序
 * 优化1：判断元素是否交换过？整个一轮排序，没有一个元素交换，那么这个数组已然有序。
 * 优化2：元素存在交换过，但是部分存在有序区间，就是对数列【无序数列的边界】的判定。
 * 》》》鸡尾酒排序
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
            // 优化1：提前退出冒泡排序的标志位
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
            // 没有数据交换，表示后面的数据已经有序，提前退出大循环
            if (!flag) {
                break;
            }
        }
    }

    private static void bubbleSort1(int[] arr) {
        // 记录最后一次交换的位置
        int lastExchangeIndex = 0;
        // 无序数列得到边界，每次比较只需要比到这里为止
        int sortBorder = arr.length - 1;

        for (int i = 0; i < arr.length - 1; i++) {
            // 优化1：有序标记，每一轮的初始值都是true
            boolean flag = true;
            // j < size - i - 1 是因为第i轮的元素个数n(size - i)个元素只需要对比n-1次
            for (int j = 0; j < sortBorder; j++) {
                // 交换
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    // 因为有元素交换，所以不是有序的，标记变为false
                    flag = false;
                    // 更新为最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            // 没有数据交换，表示后面的数据已经有序，提前退出大循环
            if (flag) {
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

        int[] arr1 = new int[]{3, 4, 2, 1, 5, 6, 7, 8};
        bubbleSort1(arr1);
        System.out.println(Arrays.toString(arr1));

        int a = 1, b = 2, aa = 3, bb = 4;
        dataExchange(a, b);
        dataExchange(aa, bb);
        System.out.println("=====================================");
        dataExchange1(a, b);
        dataExchange1(aa, bb);

    }


}
