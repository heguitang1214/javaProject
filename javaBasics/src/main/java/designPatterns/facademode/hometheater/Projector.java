package designPatterns.facademode.hometheater;

/**
 * 投影仪的操作
 */
public class Projector {
    private int size = 5;

    private static Projector instance = null;

    private Projector() {

    }

    public static Projector getInstance() {
        if (instance == null) {
            instance = new Projector();
        }

        return instance;
    }

    public void on() {
        System.out.println("打开投影仪！");
    }

    public void off() {
        System.out.println("关闭投影仪！");
    }

    public void focus() {
        System.out.println("聚焦投影仪！");
    }

    public void zoom(int size) {
        this.size = size;
        System.out.println("Popcorn zoom to" + size);
    }

}
