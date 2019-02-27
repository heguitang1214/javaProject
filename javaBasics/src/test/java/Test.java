
public class Test {

    public static void main(String[] args) {

        t2();

    }

    private static void t1() {
        int n = 3, m = 8;
        n = n + m;
        m = n - m;
        n = n - m;
        System.out.println(n + ";" + m);
    }


    private static void t2() {
        int n = 3, m = 8;
        System.out.println("交换前：" + n + ";" + m);
        n = n ^ m;
        m = n ^ m;//n ^ m ^ m
        n = n ^ m;//n ^ m ^ n ^ m ^ m
        System.out.println("交换前：" + n + ";" + m);

        int x = 5;
        System.out.println(x ^ 4 );
        System.out.println(x ^ 5 ^ 5);

    }

}
