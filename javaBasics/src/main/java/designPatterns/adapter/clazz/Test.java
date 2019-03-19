package designPatterns.adapter.clazz;


import designPatterns.adapter.common.Duck;
import designPatterns.adapter.common.GreenHeadDuck;
import designPatterns.adapter.common.WildTurkey;

public class Test {
	public static void main(String[] args) {
		GreenHeadDuck duck=new GreenHeadDuck();

		WildTurkey turkey=new WildTurkey();

		Duck adapter=new TurkeyAdapter();

		System.out.println("==============火鸡==============");
		duck.quack();
		duck.fly();
		System.out.println("==============鸭子==============");
		turkey.gobble();
		turkey.fly();
		System.out.println("==============火鸡适配成鸭子==============");
		adapter.quack();
		adapter.fly();


	}
}
