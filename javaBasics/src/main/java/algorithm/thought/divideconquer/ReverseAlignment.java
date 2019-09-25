package algorithm.thought.divideconquer;


/**
 * 如何编程求出一组数据的有序对个数或者逆序对个数呢
 *
 * @author Tang
 */
public class ReverseAlignment {

    /**
     * 全局变量或者成员变量
     */
    private static int num = 0;

    /**
     * 求出一组数据的逆序对个数
     *
     * @param a 数组
     * @param n 数组长度
     * @return 逆序对个数
     */
    private static int count(int[] a, int n) {
        num = 0;
        mergeSortCounting(a, 0, n - 1);
        return num;
    }

    /**
     * 合并为有序数组，统计数量
     * 递归中递归，然后执行合并函数
     *
     * @param a 数组
     * @param p 数组起始下标
     * @param r 数组结束下标
     */
    private static void mergeSortCounting(int[] a, int p, int r) {
        if (p >= r) {
            System.out.println("归");
            return;
        }
        int q = (p + r) / 2;
        System.out.println("执行递归1前：p=" + p + ",q=" + q);
        mergeSortCounting(a, p, q);
        System.out.println("执行递归2前：p=" + p + ",q=" + q);
        mergeSortCounting(a, q + 1, r);
        System.out.println("合并函数：p=" + p + ",q=" + q);
        merge(a, p, q, r);
    }


    /**
     * 数组合并函数
     *
     * @param a 数组
     * @param p 数组起始下标
     * @param q 数组中间下标
     * @param r 数组结束下标
     */
    private static void merge(int[] a, int p, int q, int r) {
        // i为数组起始下标，j为数组中间下标 +1，k为临时数组temp的下标
        // a[i]：数组前半部分A，a[j]：数组后半部分B
        int i = p, j = q + 1, k = 0;
        int[] tmp = new int[r - p + 1];

        while (i <= q && j <= r) {
            // 有序对
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];
            } else {
                // 逆序对， 统计 p-q 之间，比 a[j] 大的元素个数
                // q是数组a的中间下标，在范围【p-q】之间，大于a[j]的元素个数就是q - i + 1
                num += (q - i + 1);
                tmp[k++] = a[j++];
            }
        }
        // 处理剩下的
        while (i <= q) {
            tmp[k++] = a[i++];
        }
        // 处理剩下的
        while (j <= r) {
            tmp[k++] = a[j++];
        }
        // 从 tmp 拷贝回 a
        for (i = 0; i <= r - p; ++i) {
            a[p + i] = tmp[i];
        }
    }


    public static void main(String[] args) {
        int[] a = {1, 5, 6, 2, 3, 4};
        System.out.println("逆序对的个数为：" + count(a, a.length));


//        int[] b = {11, 5, 6, 12, 3, 4};
//        System.out.println("逆序对的个数为：" + count(b, b.length));
//        System.out.println(Arrays.toString(b));
    }

}
