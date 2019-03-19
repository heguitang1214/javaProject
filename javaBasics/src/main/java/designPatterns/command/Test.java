package designPatterns.command;

import designPatterns.command.control.CommandModeControl;
import designPatterns.command.device.Light;
import designPatterns.command.device.Stereo;

public class Test {

    public static void main(String[] args) {
        CommandModeControl control = new CommandModeControl();
        MarcoCommand onmarco, offmarco;
        Light bedroomlight = new Light("客厅");
        Light kitchlight = new Light("卧室");
        Stereo stereo = new Stereo();

        //客厅中灯的开关
        LightOnCommand bedroomlighton = new LightOnCommand(bedroomlight);
        LightOffCommand bedroomlightoff = new LightOffCommand(bedroomlight);
        //卧室中灯的开关
        LightOnCommand kitchlighton = new LightOnCommand(kitchlight);
        LightOffCommand kitchlightoff = new LightOffCommand(kitchlight);

        //宏命令：客厅的灯 + 卧室的灯
        Command[] oncommands = {bedroomlighton, kitchlighton};
        Command[] offcommands = {bedroomlightoff, kitchlightoff};
        onmarco = new MarcoCommand(oncommands);
        offmarco = new MarcoCommand(offcommands);

        //音响的操作
        StereoOnCommand stereoOn = new StereoOnCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);
        StereoAddVolCommand stereoaddvol = new StereoAddVolCommand(stereo);
        StereoSubVolCommand stereosubvol = new StereoSubVolCommand(stereo);

        control.setCommand(0, bedroomlighton, bedroomlightoff);
        control.setCommand(1, kitchlighton, kitchlightoff);
        control.setCommand(2, stereoOn, stereoOff);
        control.setCommand(3, stereoaddvol, stereosubvol);
        //宏操作
        control.setCommand(4, onmarco, offmarco);


        control.onButton(0);
        control.undoButton();
        //control.offButton(0);
        control.onButton(1);
        control.offButton(1);
        control.onButton(2);

        control.onButton(3);
        control.offButton(3);
        control.undoButton();
        control.offButton(2);
        control.undoButton();

        control.onButton(4);
        control.offButton(4);
    }

}
