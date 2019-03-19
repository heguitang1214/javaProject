package designPatterns.factory.absfactory;


public class Test {
    public static void main(String[] args) {

        OrderPizza mOrderPizza = new OrderPizza(new LDFactory());

    }


}
