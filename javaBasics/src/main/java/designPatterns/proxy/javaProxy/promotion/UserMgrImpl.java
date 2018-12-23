package designPatterns.proxy.javaProxy.promotion;

public class UserMgrImpl implements UserMgr {

	@Override
	public void addUser() {
		System.out.println("1: 添加用户操作......");
		System.out.println("2: 修改用户操作......");
	}
	
}
