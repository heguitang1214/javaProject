package designPatterns.singleCase;

/**
 * 静态内部类实现单例
 * 这种方式跟饿汉式方式采用的机制类似，但又有不同。
 * 两者都是采用了类装载的机制来保证初始化实例时只有一个线程。
 * 不同的地方在饿汉式方式是只要Singleton类被装载就会实例化，没有Lazy-Loading的作用，
 * 而静态内部类方式在Singleton类被装载时并不会立即实例化，
 * 而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，
 * 从而完成Singleton的实例化。类的静态属性只会在第一次加载类的时候初始化，
 * 所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
 * <p>
 * 优点：避免了线程不安全，延迟加载，效率高。
 */
public class StaticInnerClass {

    private StaticInnerClass() {
    }

    //静态内部类
    private static class SingletonInstance {
        private static final StaticInnerClass INSTANCE = new StaticInnerClass();
    }

    /**
     * 1.从外部无法访问静态内部类LazyHolder，只有当调用Singleton.getInstance方法的时候，才能得到单例对象INSTANCE。
     * 2.INSTANCE对象初始化的时机并不是在单例类Singleton被加载的时候，而是在调用getInstance方法，
     * 使得静态内部类LazyHolder被加载的时候。因此这种实现方式是利用classloader的加载机制来实现懒加载，
     * 并保证构建单例的线程安全。
     */
    public static StaticInnerClass getInstance() {
        return SingletonInstance.INSTANCE;
    }
}

