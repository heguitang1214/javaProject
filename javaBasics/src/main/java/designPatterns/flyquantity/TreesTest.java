package designPatterns.flyquantity;

public class TreesTest {

	private int length = 10000000;
	private Tree[] treelst = new Tree[length];

	public TreesTest() {
		for (int i = 0; i < length; i++) {
			treelst[i] = new Tree((int) (Math.random() * length),
					(int) (Math.random() * length),
					(int) (Math.random() * length) % 5);
		}
	}

	public void display() {
		for (int i = 0, len = treelst.length; i < len; i++) {
			treelst[i].display();
		}
	}

}
