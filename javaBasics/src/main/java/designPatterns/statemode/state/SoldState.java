package designPatterns.statemode.state;

/**
 * 出售糖果状态
 */
public class SoldState implements State {
	private CandyMachine mCandyMachine;
	public SoldState(CandyMachine mCandyMachine)
	{
		this.mCandyMachine=mCandyMachine;
	}

	@Override
	public void insertCoin() {
		System.out.println("请稍等！我们在给你糖果！please wait!we are giving you a candy!");

	}

	@Override
	public void returnCoin() {
		System.out.println("你还没有插入硬币，正在给你出售糖果！you haven't inserted a coin yet!");
		
	}

	@Override
	public void turnCrank() {
		System.out.println("出售糖果的时候，转动转盘没有效果！we are giving you a candy,turning another get nothing,!");
	}

	@Override
	public void dispense() {

		mCandyMachine.releaseCandy();
		if (mCandyMachine.getCount() > 0) {
			mCandyMachine.setState(mCandyMachine.mOnReadyState);
		} else {
			System.out.println("哦，糖果用完了！Oo,out of candies");
			mCandyMachine.setState(mCandyMachine.mSoldOutState);
		}

	}

	@Override
	public void printstate() {
		System.out.println("***出售糖果状态：SoldState***");
		
	}

}
