import org.omg.CORBA.INTERNAL;

/**
 * Created by 11256 on 2018/9/6.
 * 字符串的测试
 */
public class StringTest2 {

    public static void main(String[] args) {

        /*java这个字符串是个特殊的字符串*/
        String str1 = new StringBuilder("hello").append("Word").toString();
        String str2 = new StringBuilder("Ja").append("va").toString();

        System.out.println(str2.intern() == str2);//false ??
        System.out.println(str1.intern() == str1);//true  ??

        System.out.println("==========================================================");


        String str3 = "helloWord";//直接指向常量池
        StringBuilder str4 = new StringBuilder("helloWord");//指向堆内存空间
        String str5 = new String("helloWord");//指向堆内存空间


        System.out.println(str3 == str4.toString());//false：比较内存地址，一个常量池，一个堆内存
        System.out.println(str3.equals(str4));//false：StringBuilder不是String，类型就不一致
        System.out.println(str3.equals(str4.toString()));//true：使用String.equles()
        System.out.println(str3.equals(str5));//true

        System.out.println("***********************************************************");

        final String b = "hello";
        String d = "hello";
        String c = b + "Word";
        String e = d + "Word";
        System.out.println((str3 == c));//true
        System.out.println((str3 == e));//false：str3在常量池中，但是e在堆区中，所以内存地址是不一样的，所以是不相等的
        System.out.println(str3.equals(c));//true
        System.out.println(str3.equals(e));//true

        String s = new String("helloWord");
        System.out.println(str3 == s);//false： 一个常量池，一个堆内存
        System.out.println(str3.equals(s));//true：使用String.equles()比较

        System.out.println("-------------------------------------------------------------------------");
        Integer x = 12;
        final Integer y = 10;
        Integer z = 10;

        Integer m = y + 2;
        Integer n = z + 2;

        System.out.println(x == m);//true
        System.out.println(x == n);//true
        System.out.println(x.equals(m));//true
        System.out.println(x.equals(n));//true



        Integer xx = 1002;
        final Integer yy = 1000;
        Integer zz = 1000;

        Integer mm = yy + 2;
        Integer nn = zz + 2;

        System.out.println(xx == mm);//false
        System.out.println(xx == nn);//false
        System.out.println(xx.equals(mm));//true
        System.out.println(xx.equals(nn));//true



        int xxx = 1002;
        final int yyy = 1000;
        int zzz = 1000;

        int mmm = yyy + 2;
        int nnn = zzz + 2;

        System.out.println(xxx == mmm);//true
        System.out.println(xxx == nnn);//true


        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        System.out.println("比较结果为:" + "helloWord" == "helloWord");//false
        Object o1 = new Object();
        System.out.println("比较结果为:" + o1 == o1);//false  ??
        System.out.println("aaa" == "aaa");//true


        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        o1 = null;
        System.out.println(o1 == null);


//        T t = new T();
//        System.out.println("比较结果为:" + t == t);
    }
    static class T {}
}
