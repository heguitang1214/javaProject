package baseDemo.genericparadigm;


import java.util.ArrayList;
import java.util.List;

/**
 * ？ 通配符
 */
public class Demo01 {

    private static void fun(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(100);
        print(integerList);

        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        print(stringList);
    }

    /**
     * 其中的【？】就是通配符，通配符只能出现在左边，即：不能在new 的时候使用通配符！！！
     * List<?> list = new ArrayList<String>();
     *
     * ? 它表示一个不确定的类型，它的值会在调用时确定下来
     */
    public static void print(List<?> list){
        // 编译失败：？是什么没有办法确定
        // list.add("hello");

        // 当使用通配符的时候，泛型类中返回值为泛型的方法，也作废了！
        // Object s = list.get(0);
        for (Object obj : list){
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        fun();
    }
}
