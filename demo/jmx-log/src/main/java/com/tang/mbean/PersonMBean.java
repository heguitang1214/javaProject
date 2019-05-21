package com.tang.mbean;

//Mbean对外暴露的服务管理接口
public interface PersonMBean {
	
	public void changeName(String name);
	
	public void changeAge(int age);
	
	public void changeAddress(String address);

}
