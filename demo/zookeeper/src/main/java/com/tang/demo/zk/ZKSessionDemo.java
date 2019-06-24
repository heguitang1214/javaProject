package com.tang.demo.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 通过SessionPathWard连接
 */
public class ZKSessionDemo implements Watcher {
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKSessionDemo());
//        ZooKeeper zk = new ZooKeeper("192.168.56.101:2181,192.168.56.101:2182", 5000, new ZKSessionDemo());
        // 同步连接后才会执行下面的代码
        cdl.await();
        long sessionId = zk.getSessionId();
        System.out.println("sessionId: " + sessionId);
        byte[] passwd = zk.getSessionPasswd();
        System.out.println("passwd: " + new String(passwd));

        // 用一个错误sessionID和sessionPwd去连接zk
        zk = new ZooKeeper("192.168.56.102:2181", 5000, new ZKSessionDemo(), 1L, "test".getBytes());
        // 用一个正确sessionID和sessionPwd去连接zk，没有什么需要打印的
        zk = new ZooKeeper("192.168.56.102:2181", 5000, new ZKSessionDemo(), sessionId, passwd);
        Thread.sleep(8000);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        // 同步连接上后进行倒计数
        if (KeeperState.SyncConnected == event.getState()) {
            cdl.countDown();
        }
    }
}
