package designPatterns.facademode.hometheater;

public class Screen {

    private static Screen instance = null;

    private Screen() {

    }

    public static Screen getInstance() {
        if (instance == null) {
            instance = new Screen();
        }

        return instance;
    }

    public void up() {
        System.out.println("向上调屏幕！");
    }

    public void down() {
        System.out.println("向下调屏幕！");
    }


}
