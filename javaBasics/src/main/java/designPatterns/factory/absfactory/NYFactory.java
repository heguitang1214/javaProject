package designPatterns.factory.absfactory;

import designPatterns.factory.common.NYCheesePizza;
import designPatterns.factory.common.NYPepperPizza;
import designPatterns.factory.common.Pizza;

public class NYFactory implements AbsFactory {

	
	@Override
	public Pizza CreatePizza(String ordertype) {
		Pizza pizza = null;

		if (ordertype.equals("cheese")) {
			pizza = new NYCheesePizza();
		} else if (ordertype.equals("pepper")) {
			pizza = new NYPepperPizza();
		}
		return pizza;

	}

}
