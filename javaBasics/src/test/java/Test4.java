import org.apache.commons.lang3.StringUtils;


public class Test4 {

    public static void main(String[] args) {

        String name = "2018vc45";

        String zm = name.replaceAll("[^(a-zA-Z)]","");  //取出字母

        String number = name.replaceAll("[^(0-9)]", "");   //取出数字

        String a = name.replaceAll("8", "A");

        System.out.println(a);
        System.out.println(zm);
        System.out.println(number);
        System.out.println("C".matches("[^(0-9)]"));

        String[] ss = StringUtils.split("12345**ertyujk**ertyu", "**", 2);
        System.out.println(ss.length);

    }

}
