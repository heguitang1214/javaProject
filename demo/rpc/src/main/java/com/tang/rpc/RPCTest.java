package com.tang.rpc;

import java.net.InetSocketAddress;

public class RPCTest {
    public static void main(String[] args) {
        //RPCClient远程调用服务(DemoService服务)，获取服务返回的结果
        DemoService service = RPCClient.remoteCall(DemoService.class,
            new InetSocketAddress("localhost", 8080));
        System.out.println(service.sayHello("何贵堂"));
    }
}
