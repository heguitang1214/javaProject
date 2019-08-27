package baseDemo.genericparadigm;

import java.util.ArrayList;
import java.util.List;

/**
 * 子类限定
 */
public class Demo02 {

    private static void fun() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(300);
        integerList.add(400);
        print(integerList);
    }

    /**
     * 给通配符添加了限定：
     * 只能传递Number或其子类型
     * 子类通配符对通用性产生了影响，但使用形参更加灵活
     */
    public static void print(List<? extends Number> list) {
        // 参数为泛型的方法还是不能使用
        // list.add(new Integer(200));

        // 返回值为泛型的方法可以使用
        Number num = list.get(0);
        for (Number number : list) {
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        fun();
    }
}
