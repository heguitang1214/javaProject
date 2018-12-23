package baseDemo;



/**
 * 内部类测试
 */
public class InternalClass {

    public static void main(String[] args) {
        staticMethodA();
        InternalClass internalClass = new InternalClass();
        internalClass.methodA();
        internalClass.methodB();

    }

    private String name = "外部类 name";//姓名，属性
    private static Integer age = 200;//静态年龄
    private static final String job = "程序员";
    private String phoneNumber = "131007021139";//私有属性，手机号

    private void methodA(){
        System.out.println("外部类方法methodA()...");
    }

    //外部类的静态方法，只能访问内部类的静态变量
    private static void staticMethodA(){
        System.out.println("外部类方法staticMethodA()...");
        System.out.println("访问内部类的静态变量:" + B.name);
    }

    //内部类在定义在局部
    private void methodB(){
        Integer x = 100;
        final Integer y = 10;

        System.out.println("x=" + x + ",y=" + y);

        class C{
            private String name = "局部内部类 name";
//            static Integer z = 30;
            void methodc(){
                System.out.println("姓名:" + name + ";年龄:" + age + ";角色:" + job + ";手机号:" + phoneNumber);
            }
        }
    }


    //如果内部类没有static的话，就需要实例化内部类才能调用
    //内部类在成员位置上，被成员修饰符private所修饰。
    private class A{
//        Integer number;
        //1.内部类可以访问外部类中的成员
        void methodA(){
            System.out.println("姓名:" + name + ";年龄:" + age + ";角色:" + job + ";手机号:" + phoneNumber);
        }

    }

    static class B{
        static String name = "静态B name"; //当内部类中定义了静态成员，该内部类必须是静态的
//        Integer number = 100;

        //当内部类被static修饰后，只能直接访问外部类中的static成员。出现了访问局限。
        void methodA(){
            System.out.println("年龄:" + age + ";角色:" + job);
        }
    }
}
