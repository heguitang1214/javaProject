package designPatterns.bridging.control;

public interface Control {

    void On();

    void Off();

    void setChannel(int ch);

    void setVolume(int vol);

}
