package multiThreading.demo.bankScheduling;

/**
 * 号码机
 */
public class NumberMachine {

    //单例
    private NumberMachine() {
    }

    private static NumberMachine instance = new NumberMachine();

    public static NumberMachine getInstance() {
        return instance;
    }

    /**
     * 号码机容纳的类型
     */
    private NumberManager commonManager = new NumberManager();
    private NumberManager expressManager = new NumberManager();
    private NumberManager vipManager = new NumberManager();

    public NumberManager getCommonManager() {
        return commonManager;
    }

    public NumberManager getExpressManager() {
        return expressManager;
    }

    public NumberManager getVipManager() {
        return vipManager;
    }

}
