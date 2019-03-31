package designPatterns.strategy.quackbehavior;


public class GaGaQuackBehavior implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("__GaGa__");
    }

}