package com.tang.demo.client;

import org.I0Itec.zkclient.ZkClient;

/**
 * 客户端创建zk节点
 */
public class CreateNodeDemo {
    public static void main(String[] args) {
        ZkClient client = new ZkClient("192.168.56.101:2181", 5000);
        String path = "/zk-client/c1";
        // 递归创建节点
        client.createPersistent(path, true);
    }
}
