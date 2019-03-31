package designPatterns.facademode.hometheater;

public class Stereo {

    private static Stereo instance = null;
    private int volume = 5;

    private Stereo() {

    }

    public static Stereo getInstance() {
        if (instance == null) {
            instance = new Stereo();
        }

        return instance;
    }

    public void on() {
        System.out.println("打开立体音响！");
    }

    public void off() {
        System.out.println("关闭立体音响！");
    }

    public void setVolume(int vol) {
        volume = vol;
        System.out.println("设置立体音响的声音为：" + volume);
    }

    public void addVolume() {
        if (volume < 11) {
            volume++;
            setVolume(volume);
        }

    }

    public void subVolume() {
        if (volume > 0) {
            volume--;
            setVolume(volume);
        }

    }

}
