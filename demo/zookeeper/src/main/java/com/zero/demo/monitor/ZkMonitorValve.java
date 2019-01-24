//package com.zero.demo.monitor;
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
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.zookeeper.CreateMode;
//
//public class ZkMonitorValve extends ValveBase {
//
//	// 定义ZK创建节点的路径
//	private static final String ZK_PATH = "/Tomcat";
//
//	// 定义Curator 客户端
//	private CuratorFramework client;
//
//	// 定义相应的Server Name
//	private String serverName;
//
//	@Override
//	public void invoke(Request request, Response response)
//			throws IOException, ServletException {
//		// 实例化Curator客户端，连接串、超时时间、重试机制
//		client = CuratorFrameworkFactory.builder()
//				.connectString("192.168.56.101:2181").connectionTimeoutMs(1000)
//				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
//		client.start();
//
//		try {
//			client.create().creatingParentsIfNeeded()
//					.withMode(CreateMode.EPHEMERAL)
//					.forPath(ZK_PATH + "/" + serverName, serverName.getBytes());
//			System.out.println("============> 节点注册成功：" + serverName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String getServerName() {
//		return serverName;
//	}
//
//	public void setServerName(String serverName) {
//		this.serverName = serverName;
//	}
//
//}
