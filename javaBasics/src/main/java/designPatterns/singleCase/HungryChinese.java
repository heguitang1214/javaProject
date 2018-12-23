package designPatterns.singleCase;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/14]
 *  饿汉式
 */
public class HungryChinese {

    private static final HungryChinese single = new HungryChinese();

    private HungryChinese(){}

    public static HungryChinese getInstance(){
        return single;
    }

}
