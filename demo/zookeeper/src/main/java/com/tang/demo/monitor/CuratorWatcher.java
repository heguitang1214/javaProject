package com.tang.demo.monitor;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorWatcher {

	// Curator 访问Zk的实例
	private static CuratorFramework client;

	// 实例化Curator的Watcher
	private static TreeCache treeCache;

	public static void main(String[] args) throws Exception {
		// 初始化Curator客户端实例
		client = CuratorFrameworkFactory.builder().connectString("192.168.56.101:2181")
				.connectionTimeoutMs(3000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();

		// 实例化Curator的Watcher
		treeCache = new TreeCache(client, "/Tomcat");
		// 启动WAtcher
		treeCache.start();

		// 添加节点监听
		treeCache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				if (event.getType() == Type.NODE_ADDED) {
					System.out.println("=========== ADD");
				}

				if (event.getType() == Type.NODE_REMOVED) {
					System.out.println("=========== REMOVED");
				}

				if (event.getType() == Type.NODE_UPDATED) {
					System.out.println("=========== UPDATE");
				}

				System.out.println(event.getData().getPath());
			}
		});

		Thread.sleep(Integer.MAX_VALUE);
	}

}
