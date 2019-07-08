package baseDemo.genericparadigm;

/**
 * 【泛型类】Generic
 */
public class Generic<T> {

    /**
     * 改方法不是泛型方法，它是泛型类中的方法
     */
    private T fun(T t) {
        System.out.println(t);
        return t;
    }

    /**
     * 【泛型方法】
     */
    private <G> G fun1(G g) {
        System.out.println(g);
        return g;
    }


    public static void main(String[] args) {
        Generic<String> generic = new Generic<>();
        String param = "泛型类中的方法";
        String str = generic.fun(param);
        System.out.println(">>>>>>" + str);

        Integer number = 3;
        Integer myint = generic.fun1(number);
        System.out.println(">>>>>" + myint);

        Generic generic1 = new Generic();
        // Incompatible types.
        // Required: java.lang.Integer Found: java.lang.Object
        // 不是泛型类就不会报错
//        Integer myint1 = generic1.fun1(number);
    }

}
