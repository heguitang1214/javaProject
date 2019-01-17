package java8;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Author heguitang
 * @Date 2019/1/17 10:22
 * @Version 1.0
 * @Desc
 */
public class BiFunctionTest {

    public static void main(String[] args) {

        int data1 = BiFunctionTest.compute3(2, 3, (v1, v2) -> v1 + v2); //5
        int data2 = BiFunctionTest.compute3(4, 5, (v1, v2) -> v1 - v2); //-1
        int data3 = BiFunctionTest.compute3(6, 6, (v1, v2) -> v1 * v2); //36


        System.out.println(data1);
        System.out.println(data2);
        System.out.println(data3);

        int data4 = BiFunctionTest.compute4(2, 3, (v1, v2) -> v1 + v2, v1 -> v1 * v1);
        System.out.println(data4);
    }

    private static int compute3(int a, int b, BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(a, b);
    }

    public static int compute4(int a, int b, BiFunction<Integer, Integer, Integer> biFunction,Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(a, b);
    }



}
