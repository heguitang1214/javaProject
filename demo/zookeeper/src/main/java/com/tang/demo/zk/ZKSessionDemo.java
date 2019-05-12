package com.tang.demo.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZKSessionDemo implements Watcher {
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("192.168.56.101:2181,192.168.56.101:2182", 5000, new ZKSessionDemo());
        cdl.await();
        long sessionId = zk.getSessionId();
        System.out.println("sessionId: " + sessionId);
        byte[] passwd = zk.getSessionPasswd();
        System.out.println("passwd: " + new String(passwd));

        // 用一个错误sessionID和sessionPwd去连接zk
        zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKSessionDemo(), 1l, "test".getBytes());
        // 用一个正确sessionID和sessionPwd去连接zk
        zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKSessionDemo(), sessionId, passwd);
        Thread.sleep(1);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            cdl.countDown();
        }
    }
}
