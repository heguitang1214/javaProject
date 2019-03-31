package designPatterns.statemode.state;

import java.util.Random;

/**
 * 转动装盘状态
 */
public class HasCoinState implements State {
    private CandyMachine mCandyMachine;

    public HasCoinState(CandyMachine mCandyMachine) {
        this.mCandyMachine = mCandyMachine;
    }

    @Override
    public void insertCoin() {
        System.out.println("你不能再投一枚硬币！you can't insert another coin!");

    }

    @Override
    public void returnCoin() {
        System.out.println("硬币返还！coin return!");
        mCandyMachine.setState(mCandyMachine.mOnReadyState);
    }

    @Override
    public void turnCrank() {
        System.out.println("转动转盘！crank turn...!");
        Random ranwinner = new Random();
        int winner = ranwinner.nextInt(10);
        if (winner == 0) {
            mCandyMachine.setState(mCandyMachine.mWinnerState);
        } else {
            mCandyMachine.setState(mCandyMachine.mSoldState);
        }

    }

    @Override
    public void dispense() {
    }

    @Override
    public void printstate() {
        System.out.println("***转动装盘状态：HasCoin***");

    }

}
