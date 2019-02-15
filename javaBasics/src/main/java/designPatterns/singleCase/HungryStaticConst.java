package designPatterns.singleCase;

/**
 * 饿汉式【静态常量】实现单例
 *
 *
 * 优点：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。
 *
 * 缺点：在类装载的时候就完成实例化，没有达到Lazy Loading的效果。如果从始至终从未使用过这个实例，则会造成内存的浪费。
 */
public class HungryStaticConst {

    private final static HungryStaticConst INSTANCE = new HungryStaticConst();

    private HungryStaticConst() {
    }

    public static HungryStaticConst getInstance() {
        return INSTANCE;
    }

}
