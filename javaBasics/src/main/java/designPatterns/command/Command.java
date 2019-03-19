package designPatterns.command;

/**
 * 命令接口
 */
public interface Command {
    /**
     * 运行
     */
    void execute();

    /**
     * 回退
     */
    void undo();
}
