package reflect;

import java.lang.reflect.Method;

@MyAnnotation1(age = 100, name = "注解1")
@MyAnnotation2(name = "注解2")
/**
 * 注解的属性类型:
 * 8种基本数据类型,String,Enum,Class,注解类型
 * 以上类型的一维数组
 */
public class AnnotationTest {

    public static void main(String[] args) throws Exception {
        getClassAnnotation();
        getMethodAnnotation();
    }

    //反射类上的注解
    private static void getClassAnnotation() {
        Class<AnnotationTest> clazz = AnnotationTest.class;
        //获取执行类型的注解
        MyAnnotation1 myAnnotation1 = clazz.getAnnotation(MyAnnotation1.class);
        System.out.println(myAnnotation1.age() + "," + myAnnotation1.name());
    }

    //反射方法上的注解
    private static void getMethodAnnotation() throws Exception {
        Class<AnnotationTest> clazz = AnnotationTest.class;
        Method method = clazz.getMethod("annotationTest");
        //获取执行类型的注解
        MyAnnotation2 myAnnotation2 = method.getAnnotation(MyAnnotation2.class);
        System.out.println(myAnnotation2.age() + "," + myAnnotation2.name());
    }

    @MyAnnotation2(name = "注解2")
    public void annotationTest(){
        System.out.println("这是注解的方法!");
    }


}
