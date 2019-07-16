package spring.aop.java;

/**
 * 静态代理测试
 */
public class InstrumentTest {

    /**
     * 用main()方法模拟程序入口
     * 将该demo打成jar包，然后使用jvm启动参数模拟运行
     * java -javaagent:myTransform.jar spring.aop.java.InstrumentTest
     */
    public static void main(String[] args) {
        System.out.println("this is InstrumentTest main()");
    }

}
