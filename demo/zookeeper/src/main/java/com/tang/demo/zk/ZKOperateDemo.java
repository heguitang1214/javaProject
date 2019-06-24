package com.tang.demo.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 原生客户端操作节点
 */
public class ZKOperateDemo implements Watcher {
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKOperateDemo());
        cdl.await();

        // zk原生客户端不支持级联创建节点
        // 创建持久节点
        String path1 = zk.create("/zk-test-", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Success create path: " + path1);
        // 创建临时顺序节点
        String path2 = zk.create("/zk-test-", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create path: " + path2);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            cdl.countDown();
        }
    }
}
