package designPatterns.decorator.coffeebar;


import designPatterns.decorator.coffeebar.coffee.Decaf;
import designPatterns.decorator.coffeebar.coffee.LongBlack;
import designPatterns.decorator.coffeebar.decorator.Chocolate;
import designPatterns.decorator.coffeebar.decorator.Milk;

public class Test {


	public static void main(String[] args) {
		
		Drink order;
		order=new Decaf();
		System.out.println("order1 price:"+order.cost());
		System.out.println("order1 desc:"+order.getDescription());
		
		System.out.println("****************");
		order=new LongBlack();
		order=new Milk(order);
		order=new Chocolate(order);
		order=new Chocolate(order);
		System.out.println("order2 price:"+order.cost());
		System.out.println("order2 desc:"+order.getDescription());
		
	}


}
