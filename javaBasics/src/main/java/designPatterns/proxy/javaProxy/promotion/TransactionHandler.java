package designPatterns.proxy.javaProxy.promotion;


import designPatterns.proxy.javaProxy.core.InvocationHandler;
import java.lang.reflect.Method;


public class TransactionHandler implements InvocationHandler {
	
	private Object target;
	
	public TransactionHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public void invokeMethod(Object o, Method m) {
		System.out.println("事物(Transaction)管理开始......");
		try {
			m.invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("事物(Transaction)管理提交......");
	}

}
