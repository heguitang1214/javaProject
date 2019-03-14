package com.demo.hystrix.test.interview;

/**
 * @Author: 无双老师
 * @Date: 2018/11/10
 * @Description: 一个有关Integer的面试题
 */
public class IntegerCache {

    public static void main(String[] args) {
//        testNewInteger();
        testIntegerUnboxing();
//        testIntegerAutoBoxing();
//        testIntegerAutoBoxing2();
//        testIntegerAutoBoxing3();
    }

    /**
     * 测试两个new出的Integer对象是否相等
     */
    private static void testNewInteger() {
        Integer int1 = new Integer(1);
        Integer int2 = new Integer(1);
        System.out.println(int1 == int2);
    }

    /**
     * 测试包装器和基本类型
     * int1发生自动拆箱操作 intValue
     */
    private static void testIntegerUnboxing() {
        Integer int1 = new Integer(1);
        int int2 = 1;
        System.out.println(int1 == int2);
    }

    /**
     * 测试自动装箱valueOf
     */
    private static void testIntegerAutoBoxing() {
        Integer int1 = 1;
        Integer int2 = 1;
        System.out.println(int1 == int2);

    }

    /**
     * 测试自动装箱valueOf
     */
    private static void testIntegerAutoBoxing2() {
        Integer int1 = 128;
        Integer int2 = 128;
        System.out.println(int1 == int2);
    }

    /**
     * 测试自动装箱valueOf
     */
    private static void testIntegerAutoBoxing3() {
        Integer int1 = -1;
        Integer int2 = -1;
        System.out.println(int1 == int2);
    }
}