package designPatterns.command.device;

/**
 * 音响的操作
 */
public class Stereo {
    static int volume = 0;

    public void On() {

        System.out.println("打开立体音响......");
    }

    public void Off() {

        System.out.println("关闭立体音响......");
    }

    public void SetCd() {

        System.out.println("给立体音响放入CD......");
    }

    public void SetVol(int vol) {
        volume = vol;
        System.out.println("当前立体音响的音量为：" + volume);
    }

    public int GetVol() {
        return volume;
    }

    public void Start() {
        System.out.println("Stereo Start");
    }
}
