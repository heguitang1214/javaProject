package com.zero.demo.zk;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZKChildrenDemo implements Watcher {
	private static final CountDownLatch cdl = new CountDownLatch(1);
	private static ZooKeeper zk = null;

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKChildrenDemo());
		cdl.await();

		zk.create("/zk-test", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		zk.create("/zk-test/c1", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		List<String> list = zk.getChildren("/zk-test", true);
		for (String str : list)
			System.out.println(str);

		zk.create("/zk-test/c2", "789".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState())
			if (EventType.None == event.getType() && null == event.getPath()) {
				System.out.println("Zk connected");
				cdl.countDown();
			} else if (event.getType() == EventType.NodeChildrenChanged) {
				try {
					System.out.println("Child: " + zk.getChildren(event.getPath(), true));
				} catch (Exception e) {
				}
			}
	}
}
