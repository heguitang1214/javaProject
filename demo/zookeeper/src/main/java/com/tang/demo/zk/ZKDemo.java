package com.tang.demo.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 事件监听连接机制
 */
public class ZKDemo implements Watcher {
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKDemo());
        System.out.println(zk.getState());

        try {
            cdl.await();
        } catch (Exception e) {
            System.out.println("ZK Session established.");
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            cdl.countDown();
        }
    }
}
