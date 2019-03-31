package designPatterns.bridging.control;

public class LGControl implements Control {

    @Override
    public void On() {
        System.out.println("**Open LG TV**");
    }

    @Override
    public void Off() {
        System.out.println("**Off LG TV**");
    }

    @Override
    public void setChannel(int ch) {
        System.out.println("**The LG TV Channel is setted " + ch + "**");
    }

    @Override
    public void setVolume(int vol) {
        System.out.println("**The LG TV Volume is setted " + vol + "**");
    }

}
