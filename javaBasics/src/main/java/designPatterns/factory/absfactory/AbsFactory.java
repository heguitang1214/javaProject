package designPatterns.factory.absfactory;


import designPatterns.factory.common.Pizza;

public interface AbsFactory {
	public Pizza CreatePizza(String ordertype) ;
}
