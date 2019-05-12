package com.tang.mbean;

public class Monitor implements MonitorMBean {

	@Override
	public int getOnlineSum() {
		return 100;
	}

	@Override
	public void shutdown() {
		System.out.println("关闭服务器");
	}

	@Override
	public String getMemINfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("free:").append(Runtime.getRuntime().freeMemory()).append(",\n").append("total:")
				.append(Runtime.getRuntime().totalMemory());
		return sb.toString();
	}

	@Override
	public void execScript() {
		System.err.println("成功踢人或者封停账号");
	}

}
