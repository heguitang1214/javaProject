package designPatterns.statemode.state;

public class TraditionCandyMachine {

	final static int SoldOutState = 0;
	final static int OnReadyState = 1;
	final static int HasCoin = 2;
	final static int SoldState = 3;

	private int state = SoldOutState;
	private int count = 0;

	public TraditionCandyMachine(int count) {
		this.count = count;
		if (count > 0) {
			state = OnReadyState;
		}
	}

	public void insertCoin() {
		switch (state) {
		case SoldOutState:
			System.out.println("you can't insert coin,the machine sold out!");
			break;
		case OnReadyState:
			state = HasCoin;
			System.out
					.println("you have inserted a coin,next,please turn crank!");
			break;
		case HasCoin:
			System.out.println("you can't insert another coin!");

			break;
		case SoldState:
			System.out.println("please wait!we are giving you a candy!");

			break;
		}

	}

	public void returnCoin() {
		switch (state) {
		case SoldOutState:
			System.out
					.println("you can't return,you haven't inserted a coin yet!");
			break;
		case OnReadyState:
			System.out.println("you haven't inserted a coin yet!");
			break;
		case HasCoin:

			System.out.println("coin return!");
			state = OnReadyState;

			break;
		case SoldState:
			System.out.println("sorry,you already have turned the crank!");

			break;
		}

	}

	public void turnCrank() {
		switch (state) {
		case SoldOutState:
			System.out.println("you turned,but there are no candies!");
			break;
		case OnReadyState:
			System.out.println("you turned,but you haven't inserted a coin!");
			break;
		case HasCoin:
			System.out.println("crank turn...!");
			state = SoldState;
			dispense();
			break;
		case SoldState:
			System.out
					.println("we are giving you a candy,turning another get nothing,!");
			break;
		}

	}

	private void dispense() {
		count = count - 1;
		System.out.println("a candy rolling out!");
		if (count > 0) {
			state = OnReadyState;
		} else {
			System.out.println("Oo,out of candies");
			state = SoldOutState;
		}

	}

	public void printstate() {

		switch (state) {
		case SoldOutState:
			System.out.println("***SoldOutState***");
			break;
		case OnReadyState:
			System.out.println("***OnReadyState***");
			break;
		case HasCoin:

			System.out.println("***HasCoin***");

			break;
		case SoldState:
			System.out.println("***SoldState***");
			break;
		}

	}
}
