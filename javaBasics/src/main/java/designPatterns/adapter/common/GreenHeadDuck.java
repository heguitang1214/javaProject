package designPatterns.adapter.common;

/**
 * 鸭子的具体实现
 */
public class GreenHeadDuck implements Duck {

    @Override
    public void quack() {
        System.out.println("鸭子的叫声：Ga Ga Ga......");
    }

    @Override
    public void fly() {
        System.out.println("鸭子的飞行......");
    }

}
