package designPatterns.statemode.state;

/**
 * 状态接口
 */
public interface State {
    /**
     *插入硬币
     */
    void insertCoin();

    /**
     *退出硬币
     */
    void returnCoin();

    /**
     *转动曲柄
     */
    void turnCrank();

    /**
     *分配糖果
     */
    void dispense();

    /**
     *大于状态
     */
    void printstate();
}
