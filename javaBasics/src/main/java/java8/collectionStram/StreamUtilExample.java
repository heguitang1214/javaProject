package java8.collectionStram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author heguitang
 * @Date 2019/2/15 16:50
 * @Version 1.0
 * @Desc Java8流处理集合示例
 * <p>
 * java.util.Stream表示了某一种元素的序列,在这些元素上可以进行各种操作。
 * Stream操作可以是中间操作,也可以是完结操作。
 * <p>
 * 完结操作会返回一个某种类型的值,而中间操作会返回流对象本身,
 * 并且你可以通过多次调用同一个流操作方法来将操作结果串起来。
 * <p>
 * Stream是在一个源的基础上创建出来的,例如java.util.collection中的list或者set (map不能作为stream的源)。
 * <p>
 * Stream操作往往可以通过顺序或者并行两种方式来执行。
 * public interface Stream<T> extends BaseStream<T, Stream<T>> { }
 * <p>
 * Java 8中的collections类的功能已经有所增强,你可以之直接通过调用collections.stream ()
 * 或者collection parallelstream()方法来创建一个流对象
 */
public class StreamUtilExample {

    public static void main(String[] args) {
        StreamUtilExample streamUtilExample = new StreamUtilExample();
        streamUtilExample.useStramFilter();
        System.out.println("****************************分割线********************************");
        streamUtilExample.useStreamSort();
        System.out.println("****************************分割线********************************");
        streamUtilExample.useStramMap();
        System.out.println("****************************分割线********************************");
        streamUtilExample.useStreamMatch();
        System.out.println("****************************分割线********************************");
        streamUtilExample.useStramCount();
        System.out.println("****************************分割线********************************");
        streamUtilExample.useStreamReduce();
        System.out.println("****************************分割线********************************");
        streamUtilExample.useParellelStream();
    }

    private List<String> list = new ArrayList<>();

    {
        dataInit();
    }

    /**
     * 数据初始化
     */
    private void dataInit() {
        list.add("zzz1");
        list.add("aaa2");
        list.add("bbb2");
        list.add("fff1");
        list.add("fff2");
        list.add("aaa1");
        list.add("bbb1");
        list.add("zzz2");
    }

    /**
     * 过滤
     */
    private void useStramFilter() {
        list.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);
        List<String> strList =
                list.stream().filter(s -> s.startsWith("a")).collect(Collectors.toList());
        System.out.println(strList);
    }


    /**
     * 排序
     */
    private void useStreamSort() {
        List<String> strList =
                list.stream().sorted().filter(s -> s.startsWith("a")).collect(Collectors.toList());
        System.out.println(strList);
        //输出原始集合元素，sorted只创建排序视图，不影响原来集合顺序
        System.out.println(list);
    }

    /**
     * Map
     */
    private void useStramMap() {
        list.stream().map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        List<String> strList =
                list.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(strList);
    }


    /**
     * 匹配
     */
    private void useStreamMatch() {
        //集合中是否有任意一元素匹配‘a’开头
        boolean anyStartWith = list.stream().anyMatch(s -> s.startsWith("a"));
        System.out.println(anyStartWith);

        //集合中是否所有元素匹配‘a’开头
        boolean allStartWith = list.stream().allMatch(s -> s.startsWith("a"));
        System.out.println(allStartWith);

        //集合中是否没有元素匹配‘d’开头
        boolean nonStartWith = list.stream().noneMatch(s -> s.startsWith("d"));
        System.out.println(nonStartWith);

    }


    /**
     * 统计
     */
    private void useStramCount() {
        long startWithCount = list.stream().filter(s -> s.startsWith("a")).count();
        System.out.println(startWithCount);

        System.out.println(list.stream().count());
    }


    /**
     * 削减
     */
    private void useStreamReduce() {

        Optional<String> reduce = list.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
        reduce.ifPresent(System.out::println);
        System.out.println(reduce.get());
    }

    /**
     * 并行流
     */
    private void useParellelStream() {
        //初始化一个字符串集合
        int max = 1000000;
        List<String> values = new ArrayList<>();


        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        //使用顺序流排序
        long seqenceT0 = System.nanoTime();
        values.stream().sorted();
        long seqenceT1 = System.nanoTime();

        System.out.format("seqence sort took: %d ms.", seqenceT1 - seqenceT0).println();


        //使用并行流排序
        long parallelT0 = System.nanoTime();
        values.parallelStream().sorted();
        long parallelT1 = System.nanoTime();
        System.out.format("parallel sort took: %d ms.", parallelT1 - parallelT0).println();


    }


}
