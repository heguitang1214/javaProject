package designPatterns.singleCase;

import java.lang.reflect.Constructor;

/**
 * 利用反射重复构建对象
 */
public class Test {

    /**
     * 双重校验锁的重复构建对象
     */
    private static void doubleCheckLockTest() throws Exception {
        DoubleCheckLock instance1 = DoubleCheckLock.getInstance();
        DoubleCheckLock instance2 = DoubleCheckLock.getInstance();
        System.out.println(instance1.equals(instance2));

        // 获取构造器
        Constructor constructor = DoubleCheckLock.class.getDeclaredConstructor();
        // 设置成可访问
        constructor.setAccessible(true);
        // 构造成两个不同的对象
        DoubleCheckLock instance3 = (DoubleCheckLock) constructor.newInstance();
        DoubleCheckLock instance4 = (DoubleCheckLock) constructor.newInstance();
        // 验证是否是不同的对象
        System.out.println(instance3.equals(instance4));
    }

    /**
     * 枚举构建对象
     */
    private static void enumerationTest() throws Exception {

        Enumeration enumeration1 = Enumeration.INSTANCE;
        Enumeration enumeration2 = Enumeration.INSTANCE;
        System.out.println(enumeration1.equals(enumeration2));

        //获得构造器
        Constructor con = Enumeration.class.getDeclaredConstructor();
        //设置为可访问
        con.setAccessible(true);
        //构造两个不同的对象
        Enumeration singleton1 = (Enumeration) con.newInstance();
        Enumeration singleton2 = (Enumeration) con.newInstance();
        //验证是否是不同对象
        System.out.println(singleton1.equals(singleton2));
    }


    public static void main(String[] args) throws Exception {
//        doubleCheckLockTest();
        enumerationTest();
    }
}
