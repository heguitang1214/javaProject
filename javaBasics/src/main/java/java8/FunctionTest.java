package java8;

/**
 * @Author heguitang
 * @Date 2019/1/17 10:16
 * @Version 1.0
 * @Desc function工具类
 */
public class FunctionTest {
    public static void main(String[] args) {
        String a = "xiaomeng2";
        final String b = "xiaomeng";
        String d = "xiaomeng";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));//true
        System.out.println((a == e));//false
        System.out.println(a.equals(c));//true
        System.out.println(a.equals(e));//true


        String s = new String("xiaomeng2");
        System.out.println(a == s);
        System.out.println(a.equals(s));



    }


}
