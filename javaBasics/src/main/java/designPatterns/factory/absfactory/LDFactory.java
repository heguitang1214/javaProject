package designPatterns.factory.absfactory;


import designPatterns.factory.common.LDCheesePizza;
import designPatterns.factory.common.LDPepperPizza;
import designPatterns.factory.common.Pizza;

public class LDFactory implements AbsFactory {

	@Override
	public Pizza CreatePizza(String ordertype) {
		Pizza pizza = null;

		if (ordertype.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (ordertype.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		return pizza;

	}

}
