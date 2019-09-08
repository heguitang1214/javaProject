package algorithm.demo;

/**
 * 判断一个数是否是2的整数次幂
 *
 * @author heguitang
 */
public class PowerOf2 {

    /**
     * 使用中间变量
     *
     * @param num 输入
     * @return 是否是2的整数次幂
     */
    private static boolean isPowerOf2(int num) {
        int temp = 1;

        while (temp <= num) {
            if (temp == num) {
                return true;
            }
            temp = temp << 1;
        }
        return false;
    }

    private static boolean isPowerOf2V2(int num) {
        return (num & (num - 1)) == 0;
    }


    public static void main(String[] args) {
        System.out.println("=======================isPowerOf2(...)============================");
        System.out.println(isPowerOf2(32));
        System.out.println(isPowerOf2(19));
        System.out.println("=======================isPowerOf2V2(...)============================");
        System.out.println(isPowerOf2V2(32));
        System.out.println(isPowerOf2V2(19));

    }

}
