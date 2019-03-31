package designPatterns.facademode.hometheater;

/**
 * DVD的操作
 */
public class DVDPlayer {

    private static DVDPlayer instance = null;

    private DVDPlayer() {
    }

    public static DVDPlayer getInstance() {
        if (instance == null) {
            instance = new DVDPlayer();
        }

        return instance;
    }

    public void on() {
        System.out.println("打开DVD播放器！");
    }

    public void off() {
        System.out.println("关闭DVD播放器！");
    }

    public void play() {
        System.out.println("播放DVD播放器！");
    }

    public void pause() {
        System.out.println("暂停DVD播放器！");
    }

    public void setdvd() {
        System.out.println("设置DVD播放器！");
    }
}
