package baseDemo.genericparadigm;

import java.util.ArrayList;
import java.util.List;

/**
 * 父类限定
 */
public class Demo03 {

    private static void fun() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(300);
        integerList.add(400);
        print(integerList);

        List<Number> numberList = new ArrayList<>();
        numberList.add(10);
        numberList.add(1.1);
        print(numberList);

        List<Object> objectList = new ArrayList<>();

        List<Number> numList = new ArrayList<Number>();
        List<Integer> intList = new ArrayList<Integer>();
        // 编译失败：addAll(Collection<Number> c),传递的是List<Integer>
        numList.addAll(intList);
    }

    /**
     * 给通配符添加了限定：
     *      只能传递Integer类型，或其父类型
     */
    public static void print(List<? super Integer> list) {
        // 参数为泛型的方法可以使用了
        list.add(new Integer(200));

        // 返回值为泛型的方法，不能使用
        // 编译报错，因为参数可能是Integer的父类型
        // Integer num = list.get(0);
        Object object = list.get(0);
        Integer num = (Integer) list.get(0);
        System.out.println("object=" + object + ",num=" + num);

        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        fun();
    }
}
