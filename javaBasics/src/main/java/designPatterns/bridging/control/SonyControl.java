package designPatterns.bridging.control;

public class SonyControl implements Control {

    @Override
    public void On() {
        System.out.println("*Open Sony TV*");
    }

    @Override
    public void Off() {
        System.out.println("*Off Sony TV*");
    }

    @Override
    public void setChannel(int ch) {
        System.out.println("*The Sony TV Channel is setted " + ch + "*");
    }

    @Override
    public void setVolume(int vol) {
        System.out.println("*The Sony TV Volume is setted " + vol + "*");
    }

}
