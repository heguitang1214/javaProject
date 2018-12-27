//package com.zero.demo.election;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//
//import org.apache.catalina.connector.Request;
//import org.apache.catalina.connector.Response;
//import org.apache.catalina.valves.ValveBase;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.cache.TreeCache;
//import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
//import org.apache.curator.framework.recipes.cache.TreeCacheListener;
//import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.CreateMode;
//
//public class ZkTomcatValve extends ValveBase {
//
//	// Curator 访问Zk的实例
//	private static CuratorFramework client;
//
//	// 实例化Curator的Watcher
//	private static TreeCache treeCache;
//
//	// 临时目录用来进行master选举。也可以理解皇位和玉玺
//	private final static String path = "/Tomcat/ActiveLock";
//
//	private String serverName;
//
//	@Override
//	public void invoke(Request request, Response response) throws IOException, ServletException {
//
//		// 初始化Curator客户端实例
//		client = CuratorFrameworkFactory.builder().connectString("192.168.56.101:2181")
//				.connectionTimeoutMs(3000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
//				.build();
//		client.start();
//
//		// 创建临时节点的过程，谁创建成功谁就是master。谁拿到玉玺或者皇位谁就是皇帝
//		try {
//			createNode(path);
//		} catch (Exception e) {
//			System.out.println("======== 夺位失败，对皇位进行监控！");
//			try {
//				addZKNodeListener(path);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//
//	}
//
//	// 不是master监听节点。派间谍监视皇位
//	private void addZKNodeListener(String path) throws Exception {
//		// 实例化Curator的Watcher
//		treeCache = new TreeCache(client, path);
//		// 启动WAtcher
//		treeCache.start();
//
//		// 添加节点监听
//		treeCache.getListenable().addListener(new TreeCacheListener() {
//			@Override
//			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//				if (event != null && event.getType() == Type.NODE_REMOVED) {
//					System.out.println("======== 皇帝（master）挂了，抢位抢钱抢女人！");
//					createNode(path);
//				}
//			}
//		});
//
//		System.out.println("======== 已经派出间谍监视皇位（master）");
//	}
//
//	private void createNode(String path) throws Exception {
//		// /Tomcat/ActiveLock
//		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
//		System.out.println("======== 创建节点成功，" + serverName + " 继位成为皇帝！");
//
//	}
//
//	public String getServerName() {
//		return serverName;
//	}
//
//	public void setServerName(String serverName) {
//		this.serverName = serverName;
//	}
//}
