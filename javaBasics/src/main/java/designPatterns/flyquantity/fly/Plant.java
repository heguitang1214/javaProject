package designPatterns.flyquantity.fly;

/**
 * 对象的接口
 *      树、草等大量对象
 */
public abstract class Plant {

    public Plant() {

    }

    public abstract void display(int xCoord, int yCoord, int age);

}
