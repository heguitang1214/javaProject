package jvm.classLoader;

/**
 * Created by 11256 on 2018/9/10.
 *  类对象,Class对象,类加载器之间的关系
 */
public class ClassObjectRel {


    public static void main(String[] args) throws Exception {
        classLoaderAndClassObjectRel();
        System.out.println("========================================================");
        classObjectAndExampleRel();
    }

    /**
     * 双向关联关系
     * 类加载器与Class对象之间的关系
     */
    private static void classLoaderAndClassObjectRel() {
//        通过class文件可以获取它的加载器
        ClassLoader classLoader = A.class.getClassLoader();
        System.out.println("通过类A.class获取到类加载器:" + classLoader);
        //类加载器可以加载类
    }

    /**
     * 单向关系
     * Class对象和实例对象的关系:一个类的实例总是引用代表这个类的Class对象
     */
    private static void classObjectAndExampleRel() throws Exception {
        A a1 = new A();
        a1.methodA();

        //反射创建对象
        Class clazz = A.class;
        A a2 = (A) clazz.newInstance();
        a2.methodA();

        //实例对象获取class对象
        A a3 = new A();
        Class clazz1 = a3.getClass();
        A a4 = (A) clazz1.newInstance();
        a4.methodA();
    }

    /**
     * 测试类
     */
    static class A{
        void methodA(){
            System.out.println("我是A类中的methodA()方法......");
        }
    }

}
