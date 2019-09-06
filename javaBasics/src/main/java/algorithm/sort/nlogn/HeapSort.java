package algorithm.sort.nlogn;

import java.util.Arrays;


/**
 * 堆排序
 *
 * @author Tang
 */
public class HeapSort {


    /**
     * 1.建堆
     *
     * @param array 原数组
     */
    private static void buildHeap(int[] array) {
        int length = array.length;
        if (length <= 1) {
            return;
        }
        // 下标是 n^2+1到 n 的节点是叶子节点，不需要堆化，因为这里仅仅是建堆
        // (arr.length - 1) / 2 为最后一个叶子节点的父节点，也就是最后一个非叶子节点，依次堆化直到根节点
        for (int i = (length - 1) / 2; i >= 0; i--) {
            heapify(array, length - 1, i);
        }
    }

    /**
     * 2. 堆排序
     *
     * @param array 原数组
     */
    private static void heapSort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        // 1、建堆
        buildHeap(array);

        // 2、排序
        int k = array.length - 1;
        while (k > 0) {
            // 将堆顶元素（最大）与最后一个元素交换位置
            swap(array, 0, k);
            // 将剩下元素重新堆化，堆顶元素变成最大元素
            heapify(array, --k, 0);
        }
    }

    /**
     * 自上往下堆化(大顶堆)
     *
     * @param array 要堆化的数组
     * @param count 最后堆元素下标
     * @param i     要堆化的元素下标
     */
    private static void heapify(int[] array, int count, int i) {
        while (true) {
            // 最大值位置
            int maxPos = i;
            if (i * 2 + 1 < count && array[i] < array[i * 2 + 1]) {
                maxPos = i * 2 + 1;
            }
            if (i * 2 + 2 < count && array[maxPos] < array[i * 2 + 2]) {
                maxPos = i * 2 + 2;
            }
            if (maxPos == i) {
                break;
            }
            swap(array, i, maxPos);
            i = maxPos;
        }
    }

    private static void swap(int[] array, int children, int parent) {
        int temp = array[children];
        array[children] = array[parent];
        array[parent] = temp;
    }

    public static void main(String[] args) {
        int[] c = new int[]{1, 7, 2, 3, 4, 9, 8, 5};
        heapSort(c);
        System.out.println(Arrays.toString(c));
    }


    /**
     * 大顶堆
     */
    static class Heap {
        // 数组，从下标1开始存储数据，浪费下表为0的空间
        private int[] array;
        // 堆可以存储的最大数据个数
        private int maxCount;
        // 堆中已经存储的数据个数
        private int count;

        /**
         * 实例化一个堆
         *
         * @param capacity 数据规模
         */
        public Heap(int capacity) {
            array = new int[capacity + 1];
            maxCount = capacity;
            count = 0;
        }


        public void insert(int data) {
            // 堆满了
            if (count >= maxCount) {
                return;
            }
            ++count;

            array[count] = data;
            int i = count;
            // i/2 > 0 是因为 i=0 没有存储数据。
            // 自下往上堆化，调整数据
            while (i / 2 > 0 && array[i] > array[i / 2]) {
                // swap() 函数作用：交换下标为 i 和 i/2 的两个元素
                swap(array, i, i / 2);
                i = i / 2;
            }
        }

        void swap(int[] array, int children, int parent) {
            int temp = array[children];
            array[children] = array[parent];
            array[parent] = temp;
        }


        public int removeMax() {
            // 堆中没有数据
            if (count == 0) {
                return -1;
            }
            int result = array[1];
            array[1] = array[count];
            --count;
            heapify(array, count, 1);
            return result;
        }


        /**
         * 自上往下堆化
         *
         * @param array 原数组
         * @param count 数组中存储的数据个数
         * @param i     当前自上向下堆化的起点
         */
        void heapify(int[] array, int count, int i) {
            while (true) {
                int maxPos = i;
                // 左节点的比较
                if (i * 2 <= count && array[i] < array[i * 2]) {
                    maxPos = i * 2;
                }
                // 右节点的比较
                if (i * 2 + 1 <= count && array[i] < array[i * 2 + 1]) {
                    maxPos = i * 2 + 1;
                }
                // 无需堆化
                if (maxPos == i) {
                    break;
                }
                swap(array, i, maxPos);
                i = maxPos;
            }
        }


    }


}
