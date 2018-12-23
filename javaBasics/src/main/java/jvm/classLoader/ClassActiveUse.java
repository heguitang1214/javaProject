package jvm.classLoader;


/**
 * Created by 11256 on 2018/9/10.
 * 类的主动使用
 */
public class ClassActiveUse {

    public static void main(String[] args) {
        //当a不是static final的时候,不会首先去初始化子类,先初始化父类
        System.out.println(Child.a);
        Child.doSomething();
//        打印结果:
//        Parent类中的静态代码块......
//        3
//        Parent类中的doSomething()方法......
    }

    static class Parent{
        static int a = 3;
//        若a为static final则最先打印出来的是3
//        static final int a = 3;

        static{
            System.out.println("Parent类中的静态代码块......");
        }

        static void doSomething(){
            System.out.println("Parent类中的doSomething()方法......");
        }
    }

    private static class Child extends Parent{
        static {
            System.out.println("Child类中的静态代码块......");
        }
    }

}
