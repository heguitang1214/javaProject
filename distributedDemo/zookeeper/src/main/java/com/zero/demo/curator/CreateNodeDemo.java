package com.zero.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * curator客户端创建zk节点
 */
public class CreateNodeDemo {
	public static void main(String[] args) throws Exception {
		String path = "/zk-client/c1";
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("47.93.194.11:2181")
				.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/zk-client/c1", "test1".getBytes());
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/zk-client/c2", "test2".getBytes());
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/zk-client/c3", "test3".getBytes());
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/zk-client/c4", "test4".getBytes());
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "test".getBytes());
	}
}
