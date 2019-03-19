package designPatterns.command.device;

/**
 * 灯光的开关
 */
public class Light {

    String loc = "";

    public Light(String loc) {
        this.loc = loc;
    }

    public void On() {

        System.out.println("打开[" + loc + "]中的灯......");
    }

    public void Off() {

        System.out.println("关闭[" + loc + "]中的灯......");
    }

}
