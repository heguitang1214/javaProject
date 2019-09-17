package algorithm.demo;

/**
 * 数据全排列
 *
 * @author Tang
 */
public class FullSort {

    /**
     * 数据全排列
     *
     * @param data 数据
     * @param n    数组长度
     * @param k    表示要处理的子数组的数据个数
     */
    private static void printPermutations(int[] data, int n, int k) {
        // 当数据规模减少到1的时候，说明全排列已经完成
        if (k == 1) {
            for (int i = 0; i < n; ++i) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < k; ++i) {
            // 交换数组 i 和最后元素 k-1 的位置
            int tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;

            // 需要处理的数据规模减少1
            printPermutations(data, n, k - 1);

            tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;
        }
    }


    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        printPermutations(a, 4, 4);
    }

}
