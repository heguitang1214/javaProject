package spring.aop.java;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * 代理类，用于注册转换器
 *
 * @author Tang
 */
public class MyAgent {

    /**
     * 注意，此处的方法名premain不是随意起的，代理类必须按照下面方法进行定义
     * JVM启动参数格式如下：-javaagent:<jarpath>{options}
     * 说明下，jarpath是代理类的jar文件，options是传递给当前代理类的一个字符串参数。
     *
     * @param args            这个参数不要小看，-javaagent启动参数的jarpath的值就会通过这个参数传递进来
     * @param instrumentation 代表JVM内部组件的实例，用于注册ClassFileTransformer
     */
    public static void premain(String args, Instrumentation instrumentation) {
        // 静态代理的方式
        // 创建我们自己的转换器，通过调用addTransformer方法进行注册到JVM
        ClassFileTransformer transformer = new MyTransform();
        instrumentation.addTransformer(transformer);
    }

}
