package com.zero.demo.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;


public class ZKAsyncDemo implements Watcher {
    private static final CountDownLatch cdl = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper("192.168.56.101:2181", 5000, new ZKAsyncDemo());
        cdl.await();

        zk.create("/zk-test-", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new IStringCallback(),
                new String("I am context"));

        zk.create("/zk-test-", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallback(), new String("I am context"));

        zk.create("/zk-test-", "789".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                new IStringCallback(), new String("I am context"));

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event:" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            cdl.countDown();
        }
    }


    static class IStringCallback implements AsyncCallback.StringCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("create path result: [" + rc + ", " + path + "," + ctx + ", real path name: " + name);
        }
    }

}


