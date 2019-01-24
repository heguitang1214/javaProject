package ai.yunxi.demo.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class MonitorAgent {
	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// 定义MBean的信息
		ObjectName mbeanInfo = new ObjectName("ai.yunxi:name=ServerMonitor");

		// 将MBean注册到MBeanServer中
		mbs.registerMBean(new Monitor(), mbeanInfo);

		// 模拟不停运行
		Thread.sleep(Integer.MAX_VALUE);
	}
}
