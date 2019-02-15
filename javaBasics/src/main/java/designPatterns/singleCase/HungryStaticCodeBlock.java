package designPatterns.singleCase;

/**
 *  饿汉式（静态代码块）实现单例模式
 *
 *  这种方式和饿汉式【静态常量】的方式其实类似，只不过将类实例化的过程放在了静态代码块中，
 *
 *  也是在类装载的时候，就执行静态代码块中的代码，初始化类的实例。优缺点和上面是一样的。
 */
public class HungryStaticCodeBlock {

    private static HungryStaticCodeBlock instance;

    static {
        instance = new HungryStaticCodeBlock();
    }

    private HungryStaticCodeBlock() {}

    public static HungryStaticCodeBlock getInstance() {
        return instance;
    }

}
