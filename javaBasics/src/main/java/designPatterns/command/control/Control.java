package designPatterns.command.control;

public interface Control {

	void onButton(int slot);

	void offButton(int slot);
	
	void undoButton();
}
