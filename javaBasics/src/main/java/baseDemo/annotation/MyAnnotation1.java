package baseDemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 保留策略(注解存活的时间)，类似目标限定
@Retention(RetentionPolicy.RUNTIME)
// 目标限定(注解作用的范围)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MyAnnotation1 {
    // (1)int类型属性==>【八种基本数据类型】
    // 使用注解的时候，可以不给带有默认值的属性赋值
    int age() default 18;

    // (2)String类型属性
    String name();

    // (3)枚举类型
    MyEnum myEnum();

    // (4)Class类型
    Class clazz();

    // (5)注解类型
    MyAnnotation2 myAnnotation2();

    // (6)数组类型==>支持类型的一维数组
    int[] array();
}
