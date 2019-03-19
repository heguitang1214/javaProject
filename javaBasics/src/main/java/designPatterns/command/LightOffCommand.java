package designPatterns.command;

import designPatterns.command.device.Light;

/**
 * 灯光的关命令
 */
public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.Off();
    }

    @Override
    public void undo() {
        light.On();
    }

}
