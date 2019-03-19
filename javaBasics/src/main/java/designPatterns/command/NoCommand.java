package designPatterns.command;

/**
 * 默认的命令：没有任何的作用
 */
public class NoCommand implements Command {

	@Override
	public void execute() {

	}

	@Override
	public void undo() {

	}

}
