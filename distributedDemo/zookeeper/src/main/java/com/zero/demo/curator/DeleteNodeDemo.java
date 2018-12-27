package com.zero.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 删除zk节点
 */
public class DeleteNodeDemo {
	public static void main(String[] args) throws Exception {
		String path = "/zk-client/c1";
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("47.93.194.11:2181")
				.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "test".getBytes());

		Stat stat = new Stat();
		byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
		System.out.println("获取到需要删除节点的数据:" + new String(bytes) + ",version为：" + stat.getVersion());
		client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);

	}
}
