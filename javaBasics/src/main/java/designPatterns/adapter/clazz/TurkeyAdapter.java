package designPatterns.adapter.clazz;

import designPatterns.adapter.common.Duck;
import designPatterns.adapter.common.WildTurkey;

/**
 * 类适配器
 * 类适配器的实现：将火鸡转换成鸭子
 */
public class TurkeyAdapter extends WildTurkey implements Duck {

    @Override
    public void quack() {
        super.gobble();
    }

    @Override
    public void fly() {
        super.fly();
        super.fly();
        super.fly();
    }
}
