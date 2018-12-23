package jvm.classFile;


import java.io.Serializable;

/**
 * Created by 11256 on 2018/9/4.
 * 复杂的字节码分析
 */
public class Test2 extends Test1 implements Serializable, Comparable {

    private static String name = "测试名";
    private int age = 100;


    public void mathod1() {
        if(age == 100) throw new RuntimeException("有问题....");
    }

    //异常和code平级
    public void mathod2() throws Exception {
        System.out.println("年龄:" + age);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("00000");
    }

}
