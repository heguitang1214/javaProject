package com.tang.demo.client;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class GetDataDemo {
	public static void main(String[] args) throws InterruptedException {
		String path = "/zk-client";
		ZkClient client = new ZkClient("47.93.194.11:2181", 5000);
		client.createEphemeral(path, "123");

//		监听节点数据变化
		client.subscribeDataChanges(path, new IZkDataListener() {
//			监听子节点的数据改变
			@Override
			public void handleDataChange(String dataPath, Object data) {
				System.out.println(dataPath + " changed: " + data);
			}
//			监听子节点的数据删除
			@Override
			public void handleDataDeleted(String dataPath) {
				System.out.println(dataPath + " deleted");
			}
		});

		System.out.println(client.readData(path).toString());
		client.writeData(path, "456");
		Thread.sleep(1000);
		client.delete(path);
		Thread.sleep(Integer.MAX_VALUE);
	}
}
