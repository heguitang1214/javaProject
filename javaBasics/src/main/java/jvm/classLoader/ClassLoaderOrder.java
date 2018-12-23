package jvm.classLoader;

/**
 * Created by 11256 on 2018/9/10.
 * 类加载器顺序
 */
public class ClassLoaderOrder {

    public static void main(String[] args) {
        Child child = new Child();
        System.out.println(child.a);
//        System.out.println(child.b);//失败
        //静态代码块:初始化类变量;构造块:初始化实例变量
        //构造块在创建对象时会被调用，每次创建对象时都会被调用，并且优先于类构造函数执行。构造块中定义的变量是局部变量。

//        打印结果
//        Parent类中的静态代码块......
//        Child类中的静态代码块......
//        Parent类中的代码块......
//        Parent类中的构造器......
//        Child类中的代码块......
//        Child类中的构造器......
    }


    static class Parent{
        int a = 33;
        static{
            System.out.println("Parent类中的静态代码块......");
        }

        {
            int b = 3;
            a = 55;
            System.out.println("Parent类中的代码块......");
        }

        Parent(){
            System.out.println("Parent类中的构造器......");
        }
    }

    private static class Child extends Parent{
        static {
            System.out.println("Child类中的静态代码块......");
        }

        Child(){
            System.out.println("Child类中的构造器......");
        }

        {
            System.out.println("Child类中的代码块......");
        }
    }

}
