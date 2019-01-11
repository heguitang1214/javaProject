package ai.yunxi.demo.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class PersonAgent {
	public static void main(String[] args) throws Exception {
		// 实例化一个Mbean Server
		MBeanServer mbServer = ManagementFactory.getPlatformMBeanServer();

		// 定义MBean的信息，格式：域名：name=MBean的name
		ObjectName mbeanInfo = new ObjectName("ai.yunxi.demo:name=PersonAgent");

		// 将MBean注册到MBeanServer中
		mbServer.registerMBean(new Person(), mbeanInfo);

		// 模拟不停运行
		Thread.sleep(Integer.MAX_VALUE);
	}
}
