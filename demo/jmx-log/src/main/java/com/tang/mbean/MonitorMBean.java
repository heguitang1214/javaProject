package com.tang.mbean;

public interface MonitorMBean {

	// 获取服务器在线人数
	public int getOnlineSum();

	// 统计服务器占用内存
	public String getMemINfo();

	// 执行服务器脚本
	public void execScript();

	// 关闭服务器
	public void shutdown();
}
