package com.zero.demo.registry;

import java.net.InetSocketAddress;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.StringUtils;

// 服务注册中心
public class ServiceRegister implements Registry {

	private CuratorFramework client;
	private String connectStr;
	private int sessionTimeout;
	private int connectTimeout;

	public ServiceRegister(String host, int sessionTimeout, int connectTimeout) throws Exception {
		this.connectStr = host;
		this.sessionTimeout = sessionTimeout;
		this.connectTimeout = connectTimeout;
		init();
	}

	public void init() throws Exception {
		// 建立ZK连接
		client = CuratorFrameworkFactory.builder().connectString(connectStr)
				.sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(connectTimeout)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();

		// 初始判断，是否有根节点
		if (client.checkExists().forPath(Registry.ROOT) == null) {
			client.create().forPath(Registry.ROOT);
		}
	}

	@Override
	public boolean register(InetSocketAddress addr, String serviceName) {
		if (addr.isUnresolved()) {
			System.err.println("服务地址不可用");
			return false;
		}

		if (StringUtils.isEmpty(serviceName)) {
			System.err.println("服务名不可用");
			return false;
		}

		try {
			// 如果一个服务器在启动了HelloService服务后，要往ZK注册的信息就是
			// /ZK_Services/ai.yunxi.demo.registry.HelloService/192.168.56.101:8080
			// /ZK_Services/ai.yunxi.demo.registry.HelloService/192.168.56.101:8081
			// /ZK_Services/ai.yunxi.demo.registry.HelloService/192.168.56.101:8082
			// /ZK_Services/ai.yunxi.demo.registry.HelloService/192.168.56.101:8083
			String serverAddr = Registry.ROOT + "/" + serviceName + addr.toString();
			if (client.checkExists().creatingParentsIfNeeded().forPath(serverAddr) == null) {
				client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
						.forPath(serverAddr);
			}

			return true;
		} catch (Exception e) {
			System.err.println("服务注册失败" + e.getMessage());
			return false;
		}
	}
}
