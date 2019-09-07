package algorithm.sort.nlogn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 快速排序：与傅里叶变换等算法并称为【二十世纪十大算法】
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


    /**
     * 双边循环法、单边循环法
     *
     * @param arr        待排序数据
     * @param startIndex 数组起始下标
     * @param endIndex   数组结束下标
     */
    private static void quickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大于或等于endIndex
        if (startIndex >= endIndex) {
            return;
        }
        // 得到基准元素：双边循环法
        int pivotIndex = partitionBilateral(arr, startIndex, endIndex);
        // 得到基准元素：单边循环法
//        int pivotIndex = partitionUnilateral(arr, startIndex, endIndex);
        // 根据基准元素，分为两部分进行递归排序、
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, endIndex);
    }

    /**
     * 分治：双边循环法，实现元素交换，让数列中的元素根据自身大小，分别交换到基准元素的左右两边
     *
     * @param arr        待交换的数组
     * @param startIndex 起始下标
     * @param endIndex   结束下标
     * @return 基准元素下标
     */
    private static int partitionBilateral(int[] arr, int startIndex, int endIndex) {
        // 取第1个位置(也可以选择随机位置)的元素作为基准元素
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;

        while (left != right) {
            // 控制right指针比较左移
            while (left < right && arr[right] > pivot) {
                right--;
            }
            // 控制left指针比较右移，所以最后left可以与pivot交换
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            // 这个时候，就需要交换两个元素，让指针可以继续移动
            // 交换left和right指针指向的元素
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }

        // pivot和指针重合点交换数据
        arr[startIndex] = arr[left];
        arr[left] = pivot;

        return left;
    }

    /**
     * 分治：单边循环法
     *
     * @param arr        数组
     * @param startIndex 起始下标
     * @param endIndex   结束下标
     * @return 小于基准元素的区域边界
     */
    private static int partitionUnilateral(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置（也可以是随机位置）的元素作为基准元素
        int pivot = arr[startIndex];
        int mark = startIndex;

        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (arr[i] < pivot) {
                mark++;
                int temp = arr[mark];
                arr[mark] = arr[i];
                arr[i] = temp;
            }
        }

        arr[startIndex] = arr[mark];
        arr[mark] = pivot;

        return mark;
    }

    /**
     * 快排的非递归实现
     *
     * @param arr        数组
     * @param startIndex 起始下标
     * @param endIndex   结束下标
     */
    private static void quickSortNoRecursion(int[] arr, int startIndex, int endIndex) {
        // 用一个集合栈来递推递归的的函数栈
        Stack<Map<String, Integer>> quickSortStack = new Stack<>();
        // 整个数列的起止下标，以哈希的形式入栈
        Map<String, Integer> rootParam = new HashMap<>();
        rootParam.put("startIndex", startIndex);
        rootParam.put("endIndex", endIndex);
        quickSortStack.push(rootParam);

        // 循环结束条件：栈为空时
        while (!quickSortStack.isEmpty()) {
            // 栈顶元素出栈，得到起止下标
            Map<String, Integer> param = quickSortStack.pop();
            // 得到基准元素位置
            int pivotIndex = partitionUnilateral(arr, param.get("startIndex"), param.get("endIndex"));

            // 根据基准元素分为两部分，把每一部分的起止下标【入栈】
            if (param.get("startIndex") < pivotIndex - 1) {
                Map<String, Integer> leftParam = new HashMap<>();

                leftParam.put("startIndex", param.get("startIndex"));
                leftParam.put("endIndex", pivotIndex - 1);
                quickSortStack.push(leftParam);
            }
            if (param.get("endIndex") > pivotIndex + 1) {
                Map<String, Integer> rightParam = new HashMap<>();

                rightParam.put("startIndex", pivotIndex + 1);
                rightParam.put("endIndex", param.get("endIndex"));
                quickSortStack.push(rightParam);
            }
        }
    }


    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 9, 8};
        quickSort(array);
        System.out.println(Arrays.toString(array));

        System.out.println("====================双边（单边）循环法===================");
        int[] array1 = new int[]{4, 4, 6, 5, 3, 2, 8, 1};
        quickSort(array1, 0, array1.length - 1);
        System.out.println(Arrays.toString(array1));

        System.out.println("====================非递归实现===================");
        int[] array2 = new int[]{4, 4, 6, 5, 3, 2, 8, 1};
        quickSortNoRecursion(array2, 0, array2.length - 1);
        System.out.println(Arrays.toString(array2));

    }

}
