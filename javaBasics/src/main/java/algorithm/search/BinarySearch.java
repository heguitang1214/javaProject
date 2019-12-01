package algorithm.search;


/**
 * 二分查找
 *
 * @author Tang
 */
public class BinarySearch {


    /**
     * 数组的二分查找
     *
     * @param a     原数组
     * @param n     数组长度
     * @param value 待查找值
     * @return 对应的数组下标
     */
    private static int search(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == value) {
                return mid;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 数组的二分查找L：递归实现
     *
     * @param a     原数组
     * @param n     数组长度
     * @param value 待查找值
     * @return 对应的数组下标
     */
    private static int bsearch(int[] a, int n, int value) {
        return bsearchInternally(a, 0, n - 1, value);
    }

    /**
     * 数组的二分查找L：递归实现
     *
     * @param a     原数组
     * @param low   数组起始下标
     * @param high  数组结束下标
     * @param value 待查找值
     * @return 对应的数组下标
     */
    private static int bsearchInternally(int[] a, int low, int high, int value) {
        if (low > high) {
            return -1;
        }

        int mid = low + ((high - low) >> 1);
        if (a[mid] == value) {
            return mid;
        } else if (a[mid] < value) {
            return bsearchInternally(a, mid + 1, high, value);
        } else {
            return bsearchInternally(a, low, mid - 1, value);
        }
    }


    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] b = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(search(a, a.length, 3));
        System.out.println(search(b, b.length, 3));

        System.out.println("=======================递归实现===================");

        System.out.println(bsearch(a, a.length, 3));
        System.out.println(bsearch(b, b.length, 3));

    }


}
