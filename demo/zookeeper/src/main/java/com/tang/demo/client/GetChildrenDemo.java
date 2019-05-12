package com.tang.demo.client;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 监听子节点的变化
 */
public class GetChildrenDemo {
	public static void main(String[] args) throws InterruptedException {
		String path = "/zk-client";
		ZkClient client = new ZkClient("47.93.194.11:2181", 5000);

//		监听子节点的变化
		client.subscribeChildChanges(path, new IZkChildListener() {
//			监听子节点的变化
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) {
				System.out.println(parentPath + "的子发生变化: " + currentChilds);
			}
		});
		//创建节点/zk-client
		client.createPersistent(path);
		Thread.sleep(1000);
		System.out.println(client.getChildren(path));
		//在节点/zk-client下创建c1节点
		client.createPersistent(path + "/c1");
		Thread.sleep(1000);
		//删除节点
		client.delete(path + "/c1");
		Thread.sleep(1000);
		//删除节点
		client.delete(path);
		Thread.sleep(Integer.MAX_VALUE);
	}
}
