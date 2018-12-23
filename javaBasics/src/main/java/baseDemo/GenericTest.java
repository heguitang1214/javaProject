package baseDemo;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * 泛型的使用
 */
public class GenericTest {

    public static void main(String[] args) {
        Demo<String> demo = new Demo<>();
        demo.classGeneric("只能是字符串");//指定了类型就不会出现警告
        Demo<Integer> demo1 = new Demo<>();
        demo1.classGeneric(100);
        System.out.println("============================================================================");

        demo.show("Object类型的参数");
        System.out.println("============================================================================");

        String resultStr = demo.print("获取对应的返回值!");//返回值的类型根据参数类型决定
        System.out.println("方法返回值:" + resultStr);
        Integer resultInt = demo.print(2000);//返回值的类型根据参数类型决定
        System.out.println("方法返回值:" + resultInt);
        String str = demo.print1("方法返回值为泛型类上的返回值");
        System.out.println("方法返回值:" + str);
        System.out.println("============================================================================");


        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("ccc");
        list.add("bbb");
        ArrayList<Integer> listInt = new ArrayList<>();
        listInt.add(200);
        listInt.add(300);
        listInt.add(100);
        System.out.println("===============================泛型限定================================");
        Demo.printColl(list);
        Demo.printColl1(list);
        Demo.printColl2(listInt);

    }


    //泛型类
    static class Demo<G> {
        void classGeneric(G g) {
            System.out.println("泛型类的参数是:" + g);
        }

        //泛型方法
        public <T> void show(T t) {
            System.out.println("泛型方法,show():" + t);
        }

        //泛型方法,有返回值
        public <Q> Q print(Q q) {
            System.out.println("泛型方法[返回值],print():" + q);
            return q;
        }

        public G print1(G g) {
            System.out.println("泛型方法[返回值],print1():" + g);
            return g;
        }

        public static <A> void staticMethod(A a) {
            System.out.println("静态泛型方法,staticMethod():" + a);
        }

        //?代表不明确类型,是占位符,不能使用类型特有的方法
        static void printColl(ArrayList<?> arrayList) {
            for (Iterator<?> it = arrayList.iterator(); it.hasNext(); ) {
                System.out.println("printColl():" + it.next());
            }
        }

        //T代表具体类型,可以接受并操作T类型
        static <T> void printColl1(ArrayList<T> arrayList) {
            for (Iterator<T> it = arrayList.iterator(); it.hasNext(); ) {
//                System.out.println("eqwe");
                System.out.println("printColl1():" + it.next());
            }
        }

        /*
        ArrayList<Number> list1 = new ArrayList<>(Integer);泛型限定的就是同一类
        Number是Integer的父类,也会编译错误,因为子类是具体的实体(泛型),
        而父类是一个广泛的实体,所以编译错误,可用泛型限定来解决.
        除[? extends E]外,还有[? super E]
         */
        static void printColl2(ArrayList<? extends Number> arrayList) {
            for (Iterator<? extends Number> it = arrayList.iterator(); it.hasNext(); ) {
                System.out.println(it.next());
            }
        }

    }


}
