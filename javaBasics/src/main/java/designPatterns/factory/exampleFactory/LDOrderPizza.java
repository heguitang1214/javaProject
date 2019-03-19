package designPatterns.factory.exampleFactory;


import designPatterns.factory.common.LDCheesePizza;
import designPatterns.factory.common.LDPepperPizza;
import designPatterns.factory.common.Pizza;

public class LDOrderPizza extends OrderPizza {

	@Override
	Pizza createPizza(String ordertype) {
		Pizza pizza = null;

		if (ordertype.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (ordertype.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		return pizza;

	}

}
