package designPatterns.factory.exampleFactory;


import designPatterns.factory.common.NYCheesePizza;
import designPatterns.factory.common.NYPepperPizza;
import designPatterns.factory.common.Pizza;

public class NYOrderPizza extends OrderPizza {

	@Override
	Pizza createPizza(String ordertype) {
		Pizza pizza = null;

		if (ordertype.equals("cheese")) {
			pizza = new NYCheesePizza();
		} else if (ordertype.equals("pepper")) {
			pizza = new NYPepperPizza();
		}
		return pizza;

	}

}
