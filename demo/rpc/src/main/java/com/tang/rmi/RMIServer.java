package com.tang.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// 服务的提供者
public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        // 13号技师的名字
        String name = "com.zero.rmi.RMIDemoService";

        // 创建服务
        RMIDemoService service = new RMIDemoServiceImpl();

        // 创建本机 1099 端口上的 RMI 注册表
        Registry registry = LocateRegistry.createRegistry(1099);

        // 将服务绑定到注册表中  完成注册动作（对外提供服务）
        // 13号技师打开开始服务
        registry.bind(name, service);
    }
}
