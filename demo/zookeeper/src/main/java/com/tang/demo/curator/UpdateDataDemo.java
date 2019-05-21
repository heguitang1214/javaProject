package com.tang.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class UpdateDataDemo {
	public static void main(String[] args) throws Exception {
		String path = "/zk-client/c1";
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("47.93.194.11:2181")
				.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "test".getBytes());
		Stat stat = new Stat();
		client.getData().storingStatIn(stat).forPath(path);
		System.out.println("Current data: " + stat.getVersion());
		System.out.println("Update data: "
				+ client.setData().withVersion(stat.getVersion()).forPath(path, "some".getBytes()).getVersion());
	}
}
