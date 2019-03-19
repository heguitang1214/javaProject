package designPatterns.adapter.object;

import designPatterns.adapter.common.Duck;
import designPatterns.adapter.common.Turkey;

/**
 * 对象适配器
 */
public class TurkeyAdapter implements Duck {
    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
        System.out.println("这里可以模拟鸭子叫，或者是直接调用火鸡的叫声***");
    }

    @Override
    public void fly() {
        for (int i = 0; i < 6; i++) {
            turkey.fly();
        }
    }

}
