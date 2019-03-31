package designPatterns.strategy.quackbehavior;


public class GeGeQuackBehavior implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("__GeGe__");
    }

}
