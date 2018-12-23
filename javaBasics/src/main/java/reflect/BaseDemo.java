package reflect;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * 反射基础使用
 */
public class BaseDemo {

    public static void main(String[] args) throws Exception {
        /*
        反射执行方法
         */
        handleMethodParam();
        System.out.println("==============================华丽分割线==================================");
        handleMethod();
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }

        /*
        反射获取属性值
         */
        getAttributes();
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
        /*
        获取类信息
         */
        getClassInfo();

    }


    /**
     * 执行有参数的方法
     * method.invoke(catalinaDaemon, (Object [])null);
     */
    private static void handleMethodParam() throws Exception {
        String str = "heguitang";
        Class<?> paramTypes[] = new Class[1];
        Object param[] = new Object[1];
        paramTypes[0] = str.getClass();
        param[0] = str;
        Method method = Test.class.getMethod("getMethod", paramTypes);
        //静态方法中调用当前的内部类,需要使用静态类
//        Test test = new Test();
//        method.invoke(test, param);
        Object o1 = method.invoke(Test.class.newInstance(), param);
        System.out.println("获取反射的返回值[有返回值]:" + o1);
    }

    /**
     * 执行没有参数的方法
     */
    private static void handleMethod() throws Exception {
        Method method = Test.class.getMethod("getMethod");
        Object o2 = method.invoke(Test.class.newInstance());
        System.out.println("获取反射的返回值[无返回值]:" + o2);
    }

    /**
     * 反射获取属性
     */
    private static void getAttributes() throws Exception {
        Field[] fields = Test.class.getFields();
        for (Field field : fields) {
            Object o = field.get(Test.class.newInstance());
            System.out.println("获取公共的属性名为:" + field.getName() + ";获取公共的属性值为:" + o);
        }
        System.out.println("==============================华丽分割线==================================");
        Field[] fields1 = Test.class.getDeclaredFields();
        for (Field field : fields1) {
            //打开私有访问
            field.setAccessible(true);
            Object o = field.get(Test.class.newInstance());
            System.out.println("获取类的属性名为:" + field.getName() + ";获取类的属性值为:" + o);
        }
        System.out.println("==============================华丽分割线==================================");
        //获取指定的公共属性
        Field field = Test.class.getField("age");
        Object o = field.get(Test.class.newInstance());
        System.out.println("获取指定属性[" + field.getName() + "]的值为[" + o + "]");
        System.out.println("==============================华丽分割线==================================");
        //获取指定的私有属性的属性值
        Field field1 = Test.class.getDeclaredField("name");
        field1.setAccessible(true);
        Object o1 = field1.get(Test.class.newInstance());
        System.out.println("获取指定属性[" + field1.getName() + "]的值为[" + o1 + "]");
    }

    private static void getClassInfo() throws Exception {
        Package aPackage = Test.class.getPackage();
        System.out.println("获取类Test的包名:" + aPackage.getName());
        URL url = Test.class.getResource("");
        System.out.println("获取当前类所在的绝对路径:" + url.getPath());
        URL url1 = Test.class.getResource("/");
        System.out.println("获取当前类所在的绝对路径:" + url1.getPath());
//        Class clazz = Class.forName("");

    }


    static class Test {
        private Integer id = 10;
        private String name = "testName";
        private String password = "123456";
        public Integer age = 20;

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getMethod(String name) {
            return name;
        }

        public void getMethod() {
            System.out.println("这是没有返回值的方法!");
        }


    }


}
