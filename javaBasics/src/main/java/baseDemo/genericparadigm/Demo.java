package baseDemo.genericparadigm;


import java.util.ArrayList;
import java.util.List;

public class Demo {
    private static void fun(){
        Object[] objs = new Object[10];
        List list = new ArrayList();
        String[] strs = new String[10];
        List<String> strList = new ArrayList<>();

        // 编译通过，运行失败
        // java.lang.ArrayStoreException: java.lang.Integer
        Object[] objArray = new String[10];
        objArray[0] = new Integer(100);

        // 编译失败：因为编译擦除的问题，这里就是创建一个List
        // List<Object> objList = new ArrayList<>(String);
        // 如果编译通过，那么下面这句代码将能在JVM中执行，泛型也就失去了意义
        // objList.add(new Integer(100));
    }

    public static void main(String[] args) {
        fun();
    }
}
