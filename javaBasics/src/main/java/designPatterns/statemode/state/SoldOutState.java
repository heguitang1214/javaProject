package designPatterns.statemode.state;

/**
 * 售罄状态
 */
public class SoldOutState implements State {

    private CandyMachine mCandyMachine;

    public SoldOutState(CandyMachine mCandyMachine) {
        this.mCandyMachine = mCandyMachine;
    }

    @Override
    public void insertCoin() {
        System.out.println("你不能投硬币，机器中的糖果已经卖完了！");

    }

    @Override
    public void returnCoin() {
        System.out.println("你不能退回硬币，因为你还没有插入硬币！");
    }

    @Override
    public void turnCrank() {
        System.out.println("你转动转盘，但是没有糖果！you turned,but there are no candies!");

    }

    @Override
    public void dispense() {

    }

    @Override
    public void printstate() {
        System.out.println("***售罄状态：SoldOutState***");
    }

}
