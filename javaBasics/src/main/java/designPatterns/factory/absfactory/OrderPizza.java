package designPatterns.factory.absfactory;

import designPatterns.factory.common.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderPizza {
    AbsFactory mFactory;

    public OrderPizza(AbsFactory mFactory) {

        setFactory(mFactory);
    }

    public void setFactory(AbsFactory mFactory) {
        Pizza pizza = null;
        String ordertype;

        this.mFactory = mFactory;

        do {
            ordertype = gettype();
            pizza = mFactory.CreatePizza(ordertype);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }

        } while (true);

    }

    private String gettype() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out.println("input pizza type:");
            String str = strin.readLine();

            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
