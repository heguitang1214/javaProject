package designPatterns.decorator.coffeebar.coffee;

import designPatterns.decorator.coffeebar.Drink;

public  class Coffee extends Drink {

	@Override
	public float cost() {
		return super.getPrice();
	}

	
}
