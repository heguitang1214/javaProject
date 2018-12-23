package designPatterns.proxy.javaProxy.promotion;


import designPatterns.proxy.javaProxy.core.InvocationHandler;
import designPatterns.proxy.javaProxy.core.Proxy;

/**
 *	目标对象经过多个代理对象代理
 */
public class ClientTest {
	public static void main(String[] args) throws Exception {
		UserMgr mgr = new UserMgrImpl();
		//将用户信息进行事物代理
		InvocationHandler h = new TransactionHandler(mgr);
		//再进行时间代理
		InvocationHandler handler2 =
				new TimeHandler(Proxy.newProxyInstance(UserMgr.class, h));
		UserMgr u = (UserMgr) Proxy.newProxyInstance(UserMgr.class, handler2);
		u.addUser();
	}
//	代理类对象[class proxy.classpath.$Proxy1]
//	代理类对象[class proxy.classpath.$Proxy1]
//	invokeMethod before.....
//	时间管理器开始......
//	invokeMethod before.....
//	事物(Transaction)管理开始......
//			1: 添加用户操作......
//			2: 修改用户操作......
//	事物(Transaction)管理提交......
//	invokeMethod after.....
//	时间管理器结束......
//	invokeMethod after.....
}



