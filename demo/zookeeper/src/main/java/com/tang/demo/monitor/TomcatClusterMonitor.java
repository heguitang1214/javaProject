package com.tang.demo.monitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class TomcatClusterMonitor {
	// 定义ZK节点监控的路径
	private static final String ZK_PATH = "/Tomcat";

	// 定义Curator 客户端
	private static CuratorFramework client;

	// 定义Curator Zk Watcher实例
	private static TreeCache treeCache;

	private static FileOutputStream out;

	public static void main(String[] args) throws Exception {
		// 定义写入File路径
		File file = new File("F:/tomcat.txt");

		// 定义FileOutputStream
		out = new FileOutputStream(file);

		// 初始化Curator客户端实例
		client = CuratorFrameworkFactory.builder()
				.connectString("192.168.56.101:2181").connectionTimeoutMs(1000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();

		// 实例化Curator的zk Watcher
		treeCache = new TreeCache(client, ZK_PATH);
		// 启动watcher
		treeCache.start();

		// 添加事件的监听
		treeCache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client,
					TreeCacheEvent event) throws Exception {
				if (event.getType() == Type.NODE_REMOVED) {
					System.out.println("===========: node removed"
							+ event.getData().getData());
					send(event.getData().getData());
				}
			}
		});
		
		Thread.sleep(Integer.MAX_VALUE);
	}

	//类似短信发送
	private static void send(byte[] data) throws IOException {
		out.write(data);
		out.write("\r\n".getBytes());
		out.flush();
	}
}
