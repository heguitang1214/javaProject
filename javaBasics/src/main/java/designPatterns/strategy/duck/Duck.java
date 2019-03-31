package designPatterns.strategy.duck;

import designPatterns.strategy.flybehavior.FlyBehavior;
import designPatterns.strategy.quackbehavior.QuackBehavior;

/**
 * 通过接口来组装鸭子的行为
 */
public abstract class Duck {

    FlyBehavior mFlyBehavior;
    QuackBehavior mQuackBehavior;

    public Duck() {

    }

    public void Fly() {
        mFlyBehavior.fly();
    }

    public void Quack() {
        mQuackBehavior.quack();
    }

    public abstract void display();

    public void SetQuackBehavoir(QuackBehavior qb) {
        mQuackBehavior = qb;
    }

    public void SetFlyBehavoir(FlyBehavior fb) {
        mFlyBehavior = fb;
    }

    public void swim() {
        System.out.println("~~im swim~~");
    }
}
