package designPatterns.command;

import designPatterns.command.device.Light;

/**
 * 灯光的开命令
 */
public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;

    }

    @Override
    public void execute() {
        light.On();
    }

    @Override
    public void undo() {
        light.Off();
    }

}
