package spring.aop.java;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 模拟Java转换器的具体实现
 *
 * @author Tang
 */
public class MyTransform implements ClassFileTransformer {

    /**
     * 重写 transform方法，实现我们自身的增强逻辑
     */
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("获取当前的ClassLoader:" + loader.getClass());
        System.out.println("模拟AOP织入所需的功能，打印当前类名：" + className);
        // 并不是将加载的类字节码置空；返回为空，则表示不进行节码转换处理
        return null;
    }
}
