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
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.CreateMode;
//
//public class MasterElection extends ValveBase {
//
//	private static CuratorFramework client;
//
//	// 皇位所在地
//	private final static String zkPath = "/Tomcat/ActiveLock";
//
//	// 节点监听
//	private static TreeCache treeCache;
//
//	@Override
//	public void invoke(Request request, Response response) throws IOException, ServletException {
//		client = CuratorFrameworkFactory.builder().connectString("192.168.56.101:2181").connectionTimeoutMs(1000)
//				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
//		client.start();
//
//		try {
//			// 创建节点
//			createZkNode(zkPath);
//		} catch (Exception e) {
//			System.out.println("====== 夺位失败，派出间谍监控皇位！");
//			try {
//				addZkNodeListener(zkPath);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//
//	private void addZkNodeListener(String path) throws Exception {
//		treeCache = new TreeCache(client, path);
//		treeCache.start();
//		treeCache.getListenable().addListener(new TreeCacheListener() {
//			@Override
//			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
//				if (event.getData()!=null && event.getType() == TreeCacheEvent.Type.NODE_REMOVED){
//					System.out.println("====== 皇帝挂了！赶紧抢钱，抢粮，抢女人！");
//					createZkNode(zkPath);
//				}
//			}
//		});
//	}
//
//	private void createZkNode(String path) throws Exception {
//		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
//		System.out.println("====== ZK创建成功，夺位成功，单选为皇帝！");
//	}
//}
