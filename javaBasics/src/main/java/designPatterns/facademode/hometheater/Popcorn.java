package designPatterns.facademode.hometheater;

/**
 * 爆米花的操作
 */
public class Popcorn {

    private static Popcorn instance = null;

    private Popcorn() {

    }

    public static Popcorn getInstance() {
        if (instance == null) {
            instance = new Popcorn();
        }

        return instance;
    }

    public void on() {
        System.out.println("打开爆米花！");
    }

    public void off() {
        System.out.println("关闭爆米花！");
    }

    public void pop() {
        System.out.println("在吃爆米花！");
    }


}
