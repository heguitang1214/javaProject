package designPatterns.statemode.state;

/**
 * 幸运状态：糖果发放+1
 */
public class WinnerState implements State {

    private CandyMachine mCandyMachine;

    public WinnerState(CandyMachine mCandyMachine) {
        this.mCandyMachine = mCandyMachine;
    }

    @Override
    public void insertCoin() {
        System.out.println("现在不能插入硬币，我们在给你糖果！please wait!we are giving you a candy!");

    }

    @Override
    public void returnCoin() {
        System.out.println("你还没有插入硬币！you haven't inserted a coin yet!");

    }

    @Override
    public void turnCrank() {
        System.out.println("幸运状态的时候，转动转盘没有效果！we are giving you a candy,turning another get nothing,!");

    }

    @Override
    public void dispense() {
        mCandyMachine.releaseCandy();
        if (mCandyMachine.getCount() == 0) {
            mCandyMachine.setState(mCandyMachine.mSoldOutState);
        } else {
            System.out.println("你是赢家！你再拿一个糖果！you are a winner!you get another candy!");
            mCandyMachine.releaseCandy();
            if (mCandyMachine.getCount() > 0) {
                mCandyMachine.setState(mCandyMachine.mOnReadyState);
            } else {
                System.out.println("哦，糖果用完了！Oo,out of candies");
                mCandyMachine.setState(mCandyMachine.mSoldOutState);
            }
        }

    }

    @Override
    public void printstate() {
        System.out.println("***WinnerState***");
    }

}
