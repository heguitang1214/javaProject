package designPatterns.strategy.flybehavior;


public class GoodFlyBehavior implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("--GoodFly--");
    }

}