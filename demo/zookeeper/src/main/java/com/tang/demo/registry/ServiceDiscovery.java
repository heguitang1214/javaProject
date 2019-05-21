package com.tang.demo.registry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

// 服务发现
public class ServiceDiscovery {
	private static CuratorFramework client;

	private static TreeCache treeCache;

	private static final String ROOT = "/ZK_Services";

	// 保存服务实例
	private static Map<String, List<String>> serviceMap = new ConcurrentHashMap<>();

	static {
		init();
		nodeMonitor();
	}

	// 初始化ZK连接，初始化缓存数据serviceMap
	public static void init() {
		client = CuratorFrameworkFactory.builder().connectString("192.168.56.101:2181")
				.connectionTimeoutMs(3000).sessionTimeoutMs(3000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();

		try {
			List<String> serviceList = client.getChildren().forPath(ROOT);
			for (String serviceName : serviceList) {
				List<String> servers = client.getChildren().forPath(ROOT + "/" + serviceName);
				serviceMap.put(serviceName, servers);
			}
		} catch (Exception e) {
			System.err.println("初始化失败: " + e.getMessage());
		}
	}

	public static void nodeMonitor() {
		treeCache = new TreeCache(client, ROOT);

		try {
			treeCache.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		treeCache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				String data = new String(event.getData().getData());
				// 新的服务上线
				if (event.getType() == Type.NODE_ADDED) {
					System.out.println("========ADD");
					addServer(event.getData().getPath(), data);
				}

				// 服务信息产生变化，或者更新负载
				if (event.getType() == Type.NODE_UPDATED) {
					// serviceMap.put(event.getData().getPath(), data);
				}

				// 服务下线
				if (event.getType() == Type.NODE_REMOVED) {
					removeServer(event.getData().getPath(), data);
					treeCache.getListenable().removeListener(this);
				}
			}
		});
	}

	public static Map<String, List<String>> getServiceMap() {
		return serviceMap;
	}

	public static List<String> getServers(String serviceName) {
		return serviceMap.get(serviceName);
	}

	public static void addServer(String serviceName, String server) {
		List<String> list = serviceMap.get(serviceName);
		if (!list.contains(server))
			list.add(server);

		serviceMap.put(serviceName, list);
	}

	public static void removeServer(String serviceName, String server) {
		List<String> list = serviceMap.get(serviceName);
		if (list.contains(server))
			list.remove(server);

		serviceMap.put(serviceName, list);
	}

	// 随机获取一个可用的服务地址，作负载均衡使用
	public static Optional<String> getServer(String serviceName) {
		if (serviceMap.size() == 0 || serviceMap.get(serviceName) == null
				|| serviceMap.get(serviceName).isEmpty()) {
			System.err.println("does not have available server.");
			return Optional.ofNullable(null);
		}

		int size = serviceMap.get(serviceName).size();

		// 这里更改为其他负载均衡算法
		int rand = new Random().nextInt(size);
		System.out.println("size=" + size + ",rand=" + rand);
		String server = (String) serviceMap.get(serviceName).toArray()[rand];
		return Optional.ofNullable(server);
	}
}
