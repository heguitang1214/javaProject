package designPatterns.statemode;

import designPatterns.statemode.state.CandyMachine;

public class Test {
    public static void main(String[] args) {
        CandyMachine mCandyMachine = new CandyMachine(1);

        mCandyMachine.printstate();

        mCandyMachine.insertCoin();
        mCandyMachine.printstate();

        mCandyMachine.turnCrank();

        mCandyMachine.printstate();

        mCandyMachine.insertCoin();
        mCandyMachine.printstate();

        mCandyMachine.turnCrank();

        mCandyMachine.printstate();
    }
}
