package com.zero.demo.zk;

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
import org.apache.zookeeper.data.Stat;

public class ZKDataDemo implements Watcher {
	private static final CountDownLatch cdl = new CountDownLatch(1);
	private static ZooKeeper zk = null;
	private static Stat stat = new Stat();

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		zk = new ZooKeeper("192.168.1.129:2181", 5000, new ZKDataDemo());
		cdl.await();

		zk.create("/zk-test", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(new String(zk.getData("/zk-test", true, stat)));

		zk.getData("/zk-test", true, stat);
		System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());
		zk.setData("/zk-test", "123".getBytes(), -1);

		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
			if (EventType.None == event.getType() && null == event.getPath()) {
				cdl.countDown();
			} else if (event.getType() == EventType.NodeDataChanged) {
				try {
					System.out.println(new String(zk.getData(event.getPath(), true, stat)));
					System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
