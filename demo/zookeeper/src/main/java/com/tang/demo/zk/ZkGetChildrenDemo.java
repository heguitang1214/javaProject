package com.tang.demo.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkGetChildrenDemo implements Watcher {
	private static ZooKeeper zk = null;
	private static CountDownLatch cdl = new CountDownLatch(1);

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		zk = new ZooKeeper("192.168.1.129:2181", 5000, new ZkGetChildrenDemo());
		cdl.await();

		String path = "/zk-client";
		zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		Thread.sleep(1000);
		zk.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		Thread.sleep(1000);
		zk.delete(path+"/c1", -1);
		Thread.sleep(1000);
		zk.delete(path, -1);
		Thread.sleep(1000);
	}

	@Override
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState())
			if (EventType.None == event.getType() && null == event.getPath()) {
				System.out.println("Zk Connected");
				cdl.countDown();
			} else if (event.getType() == EventType.NodeChildrenChanged) {
				try {
					System.out.println("Child: " + zk.getChildren(event.getPath(), true));
				} catch (Exception e) {
				}
			}
	}
}
