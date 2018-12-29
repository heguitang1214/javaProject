package com.zero.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// 服务的消费者
public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        // 服务名字
        String name = "com.zero.rmi.RMIDemoService";

        // 获取注册   找到注册中心
        // 找到大宝剑的地方
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);

        // 查找对应的服务   找到相应服务
        // 点13号技师
        RMIDemoService service = (RMIDemoService) registry.lookup(name);

        // 调用服务
        // 开始服务
        System.out.println(service.sayHello("张三"));
    }
}
