package designPatterns.statemode.state;

/**
 * 待机状态
 */
public class OnReadyState implements State {
    private CandyMachine mCandyMachine;

    public OnReadyState(CandyMachine mCandyMachine) {
        this.mCandyMachine = mCandyMachine;
    }

    @Override
    public void insertCoin() {
        System.out.println("你投入银币，下一步，需要转动转盘！you have inserted a coin,next,please turn crank!");
        mCandyMachine.setState(mCandyMachine.mHasCoin);
    }

    @Override
    public void returnCoin() {
        System.out.println("你还没有插入硬币！you haven't inserted a coin yet!");

    }

    @Override
    public void turnCrank() {
        System.out.println("你想退回银币，但是你还没有插入硬币！you turned,but you haven't inserted a coin!");

    }

    @Override
    public void dispense() {

    }

    @Override
    public void printstate() {
        System.out.println("***待机状态：OnReadyState***");
    }

}
